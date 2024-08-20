import React from 'react';
import './Footer.css'; // Create this CSS file

const Footer = () => {
    return (
        <footer className="footer">
            <p>&copy; {new Date().getFullYear()} Desk Booking App. All rights reserved.</p>
        </footer>
    );
};

export default Footer;
