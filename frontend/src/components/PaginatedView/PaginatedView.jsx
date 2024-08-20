import React, { useState, useEffect } from 'react';
import axios from 'axios';

const PaginatedView = () => {
    const [bookings, setBookings] = useState([]);
    const [page, setPage] = useState(0);
    const [size] = useState(10); // Adjust the size as per your requirement
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetchBookings(page, size);
    }, [page, size]);

    const fetchBookings = async (page, size) => {
        try {
            const token = localStorage.getItem('jwt');
            const response = await axios.get('http://localhost:8080/history-booking/get-history-bookings', {
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                params: {
                    page,
                    size
                }
            });
            const totalElements = response.data.length;
            setBookings(response.data);
            setTotalPages(Math.ceil(totalElements / size)); // Adjusting for static data
        } catch (error) {
            console.error('Error fetching booking history', error);
        }
    };

    const handlePreviousPage = () => {
        if (page > 0) setPage(page - 1);
    };

    const handleNextPage = () => {
        if (page < totalPages - 1) setPage(page + 1);
    };

    return (
        <div className="paginated-view">
            <h2>Booking History</h2>
            <table>
                <thead>
                    <tr>
                        <th>Desk Name</th>
                        <th>Assignee</th>
                        <th>Action</th>
                        <th>Booked At</th>
                        <th>End Of Booking</th>
                    </tr>
                </thead>
                <tbody>
                    {bookings.slice(page * size, (page + 1) * size).map((booking) => (
                        <tr key={booking.historyBookingId}>
                            <td>{booking.deskName}</td>
                            <td>{booking.asignee}</td>
                            <td>{booking.action}</td>
                            <td>{booking.bookedAt}</td>
                            <td>{booking.endOfBooking}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="pagination-controls">
                <button onClick={handlePreviousPage} disabled={page === 0}>
                    Previous
                </button>
                <span> Page {page + 1} of {totalPages} </span>
                <button onClick={handleNextPage} disabled={page === totalPages - 1}>
                    Next
                </button>
            </div>
        </div>
    );
};

export default PaginatedView;
