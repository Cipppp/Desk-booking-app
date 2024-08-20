import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'; // Make sure to create and import this CSS file

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-container">
                <ul className="navbar-menu">
                    <li className="navbar-item"><Link to="/">Home</Link></li>
                    <li className="navbar-item"><Link to="/dashboard">Dashboard</Link></li>
                    <li className="navbar-item"><Link to="/booking-history">Booking History</Link></li>
                    <li className="navbar-item"><Link to="/login">Login</Link></li>
                    <li className="navbar-item"><Link to="/signup">Sign Up</Link></li>
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;
