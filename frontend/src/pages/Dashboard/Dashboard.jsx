import React, { useState } from 'react';
import { LazyLoadImage } from 'react-lazy-load-image-component';
import { AiOutlineClose } from 'react-icons/ai';
import window from '../../assets/window.png';
import axios from 'axios';
import ModalDesk from '../../components/ModalDesk/ModalDesk';
import Modal from '../../components/Modal/Modal';

const desks = [
    { deskId: "d-a2", mapPosition: "A2" },
    { deskId: "d-c1", mapPosition: "C1" },
    { deskId: "d-c3", mapPosition: "C3" },
    { deskId: "d-c4", mapPosition: "C4" },
    { deskId: "d-d1", mapPosition: "D1" },
    { deskId: "d-d11", mapPosition: "D11" },
    { deskId: "d-d2", mapPosition: "D2" },
    { deskId: "d-d3", mapPosition: "D3" },
    { deskId: "d-d4", mapPosition: "D4" },
    { deskId: "d-e1", mapPosition: "E1" },
    { deskId: "d-e11", mapPosition: "E11" },
    { deskId: "d-e3", mapPosition: "E3" },
    { deskId: "d-e4", mapPosition: "E4" },
    { deskId: "d-e5", mapPosition: "E5" }
];

function Dashboard() {
    const style = { color: 'snow', fontSize: '2.5em' };
    const [modalOpen, setModalOpen] = useState(false);
    const [selectedDesk, setSelectedDesk] = useState(null);
    const [isOpen, setIsOpen] = useState(false);
    const [modalText, setModalText] = useState("");

    const handleDeskClick = (deskId) => {
        setSelectedDesk(deskId);
        setModalOpen(true);
    };

    const handleBooking = async (bookingData) => {
        try {
            const token = localStorage.getItem('jwt');
            const response = await axios.post('http://localhost:8080/booking/book', bookingData, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            alert(response.data.message);
            setModalOpen(false);
        } catch (error) {
            console.error("Error booking desk:", error);
            setIsOpen(true);
            setModalText("Error booking desk. Please try again.");
        }
    };

    const renderDesk = (deskId, onClick) => (
        <div className="bg-desk w-[6rem] h-[6rem] rounded-xl ml-8 mr-8 flex items-center justify-items-center" onClick={() => onClick(deskId)}>
            <AiOutlineClose className="m-auto text-4xl" style={style} />
        </div>
    );

    return (
        <>
            <div className="overflow-hidden flex items-center justify-items-center">
                {modalOpen && <ModalDesk setOpenModal={setModalOpen} deskId={selectedDesk} onBooking={handleBooking} />}
                <div className="p-10 h-screen grid grid-rows-6 w-screen place-items-center">
                    {/* First row of desks */}
                    <div className="grid grid-cols-10">
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                        {renderDesk('d-a2', handleDeskClick)}
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                        {renderDesk('d-c1', handleDeskClick)}
                        {renderDesk('d-c3', handleDeskClick)}
                        {renderDesk('d-c4', handleDeskClick)}
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                        {renderDesk('d-d1', handleDeskClick)}
                        {renderDesk('d-d11', handleDeskClick)}
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                    </div>

                    {/* Second row of desks */}
                    <div className="grid grid-cols-10">
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8">
                            <span className="m-auto font-black text-5xl flex flex-col rotate-90">
                                <LazyLoadImage src={window} className="p-4" />
                            </span>
                        </div>
                        {renderDesk('d-d2', handleDeskClick)}
                        {renderDesk('d-d3', handleDeskClick)}
                        {renderDesk('d-d4', handleDeskClick)}
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                        {renderDesk('d-e1', handleDeskClick)}
                        {renderDesk('d-e11', handleDeskClick)}
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                    </div>

                    {/* Third row of desks */}
                    <div className="grid grid-cols-10">
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8">
                            <LazyLoadImage src={window} className="p-4" />
                        </div>
                        {renderDesk('d-e3', handleDeskClick)}
                        {renderDesk('d-e4', handleDeskClick)}
                        {renderDesk('d-e5', handleDeskClick)}
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                        <div className="w-[6rem] h-[6rem] flex items-center justify-items-center mr-8 ml-8"></div>
                    </div>
                </div>
            </div>
            {isOpen && <Modal setIsOpen={setIsOpen} modalText={modalText} />}
        </>
    );
}

export default Dashboard;
