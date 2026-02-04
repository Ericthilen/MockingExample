package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class BookingSystemTest {

    private TimeProvider timeProvider;
    private RoomRepository roomRepository;
    private NotificationService notificationService;
    private BookingSystem bookingSystem;

    private final LocalDateTime NOW = LocalDateTime.of(2025, 1, 1, 12, 0);

    @BeforeEach
    void setup() {
        timeProvider = mock(TimeProvider.class);
        roomRepository = mock(RoomRepository.class);
        notificationService = mock(NotificationService.class);

        when(timeProvider.getCurrentTime()).thenReturn(NOW);

        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
    }

    @Test
    @DisplayName("bookRoom ska skapa bokning och returnera true när rummet är ledigt")
    void bookRoom_success() throws NotificationException {
        LocalDateTime start = NOW.plusHours(1);
        LocalDateTime end = NOW.plusHours(2);

        Room room = spy(new Room("room1", "Conference Room"));
        when(roomRepository.findById("room1")).thenReturn(Optional.of(room));
        doReturn(true).when(room).isAvailable(start, end);

        boolean result = bookingSystem.bookRoom("room1", start, end);

        assertThat(result).isTrue();
        verify(roomRepository).save(room);
        verify(notificationService).sendBookingConfirmation(any(Booking.class));
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> invalidTimes() {
        LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 0);
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(null, now.plusHours(1)),
                org.junit.jupiter.params.provider.Arguments.of(now.plusHours(1), null),
                org.junit.jupiter.params.provider.Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTimes")
    @DisplayName("bookRoom ska kasta exception vid null start/sluttid")
    void bookRoom_nullTimes(LocalDateTime start, LocalDateTime end) {
        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("bookRoom ska kasta exception när roomId är null")
    void bookRoom_nullRoomId(String roomId) {
        LocalDateTime start = NOW.plusHours(1);
        LocalDateTime end = NOW.plusHours(2);

        assertThatThrownBy(() -> bookingSystem.bookRoom(roomId, start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
