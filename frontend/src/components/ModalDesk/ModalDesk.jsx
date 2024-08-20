import React, { useState } from 'react';
import axios from 'axios';
import './ModalDesk.css';

function ModalDesk({ setOpenModal, deskId, onBooking }) {
    const [userId, setUserId] = useState(localStorage.getItem('userId'));
    const [endOfBooking, setEndOfBooking] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const bookingData = {
            deskId,
            userId,
            bookedAt: new Date().toISOString(),  // Set bookedAt to current date-time
            endOfBooking: new Date(endOfBooking).toISOString()  // Ensure date-time format is correct
        };

        onBooking(bookingData);
    };

    const handleCancelBooking = async () => {
        const cancelData = {
            userId,
            bookingId: deskId // Assuming bookingId is same as deskId for simplicity
        };

        try {
            const token = localStorage.getItem('jwt');
            const response = await axios.delete('http://localhost:8080/booking/book', {
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                data: cancelData
            });
            alert(response.data.message);
            setOpenModal(false);
        } catch (error) {
            console.error("Error cancelling booking:", error);
        }
    };

    return (
        <div className="modalBackground">
            <div className="modalContainer">
                <div className="titleCloseBtn">
                    <button onClick={() => setOpenModal(false)}>×</button>
                </div>
                <div className="body">
                    <form onSubmit={handleSubmit}>
                        <h2>Book Desk {deskId}</h2>
                        <label>
                            End of Booking:
                            <input
                                type="datetime-local"
                                value={endOfBooking}
                                onChange={(e) => setEndOfBooking(e.target.value)}
                                required
                            />
                        </label>
                        <div className="footer">
                            <button type="submit">Book</button>
                            <button id="cancelBtn" onClick={handleCancelBooking}>Cancel Booking</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default ModalDesk;
