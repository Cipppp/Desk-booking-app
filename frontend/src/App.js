import './styles/output.css';
import './components/Navbar/Navbar.css'; // Ensure this import is correct
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Dashboard, Home, Login, SignUp } from './pages';
import { Navbar } from './components';
import Footer from './components/Footer/Footer';
import PaginatedView from './components/PaginatedView/PaginatedView'; // Import the PaginatedView component

function App() {
    return (
        <>
            <Router>
                <Navbar />
                <main className="main-content">
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/signup" element={<SignUp />} />
                        <Route path="/dashboard" element={<Dashboard />} />
                        <Route path="/booking-history" element={<PaginatedView />} /> {/* Add this route */}
                    </Routes>
                </main>
                <Footer />
            </Router>
        </>
    );
}

export default App;
