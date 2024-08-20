import React, { useState } from 'react';
import { ReactComponent as SignUpImg } from '../../assets/authentication.svg';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Select from 'react-select';
import Modal from "../../components/Modal/Modal";

const actions = [
    { label: 'Option1', value: 1 },
    { label: 'Option2', value: 2 },
    { label: 'Option3', value: 3 },
];

function SignUp() {
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [isOpen, setIsOpen] = useState(false);
    const [modalText, setModalText] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const user = { firstName, lastName, email, password, roles: ['USER', 'ADMIN'] };

        try {
            const resp = await axios.post(
                'http://localhost:8080/user/register',
                user
            );
            console.log("Resp=" + JSON.stringify(resp));
            if (resp.data.message === 'The user was created.') {
                localStorage.setItem('userId', resp.data.id);
                navigate('/login');
            } else {
                setIsOpen(true);
                setModalText(resp.data.message);
            }
        } catch (error) {
            console.log("Error= " + error);
        }
    };

    return (
        <div className="lg:grid lg:grid-cols-2 items-center h-screen overflow-hidden">
            <div className="hidden lg:flex left-side h-screen justify-center items-center bg-white order-last">
                <div>
                    <SignUpImg className="p-60 w-12/12" />
                </div>
            </div>

            <div className="h-full flex justify-center items-center bg-white">
                <div className="flex w-full justify-center">
                    <div className="w-full mt-20 md:flex md:justify-center md:items-center">
                        <div className="w-screen md:w-8/12 justify-center items-center font-josefin p-8 text-sm sm:text-base md:text-lg xl:text-xl text-jet">
                            <h1 className="font-josefin font-bold flex justify-center">
                                Sign up
                            </h1>
                            <form onSubmit={handleSubmit} action="submit">
                                <h1 className="text-sm sm:text-base md:text-lg xl:text-xl">
                                    First name
                                </h1>
                                <input
                                    type="text"
                                    autoFocus
                                    required
                                    className="bg-white w-full p-3 focus:outline-none focus:border-jet rounded-3xl text-sm sm:text-base md:text-lg xl:text-xl border-4 border-jet"
                                    value={firstName}
                                    onChange={(e) => setFirstName(e.target.value)}
                                />
                                <h1 className="text-sm sm:text-base md:text-lg xl:text-xl">
                                    Last name
                                </h1>
                                <input
                                    type="text"
                                    autoFocus
                                    required
                                    className="bg-white w-full p-3 focus:outline-none focus:border-jet rounded-3xl text-sm sm:text-base md:text-lg xl:text-xl border-4 border-jet"
                                    value={lastName}
                                    onChange={(e) => setLastName(e.target.value)}
                                />
                                <h1 className="text-sm sm:text-base md:text-lg xl:text-xl">
                                    Email
                                </h1>
                                <input
                                    type="email"
                                    autoFocus
                                    required
                                    className="bg-white w-full p-3 focus:outline-none focus:border-jet rounded-3xl text-sm sm:text-base md:text-lg xl:text-xl border-4 border-jet"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                                <h1 className="text-sm sm:text-base md:text-lg xl:text-xl pt-2">
                                    Password
                                </h1>
                                <input
                                    type="password"
                                    required
                                    className="bg-white w-full p-3 focus:outline-none focus:border-jet rounded-3xl text-sm sm:text-base md:text-lg xl:text-xl border-4 border-jet"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                                <h1 className="text-sm sm:text-base md:text-lg xl:text-xl pt-2">
                                    Password confirmation
                                </h1>
                                <input
                                    type="password"
                                    required
                                    className="bg-white w-full p-3 focus:outline-none focus:border-jet rounded-3xl text-sm sm:text-base md:text-lg xl:text-xl border-4 border-jet"
                                />

                                <div className="btnContainer grid grid-rows-2 place-items-center">
                                    <>
                                        <button
                                            type="submit"
                                            className="btn-auth-login hover:bg-jet hover:text-white focus:outline-none"
                                        >
                                            Sign up
                                        </button>
                                        <p className="text-sm sm:text-base md:text-lg xl:text-xl">
                                            Already have an account?
                                            <Link
                                                to="/login"
                                                className="pl-2 text-sm sm:text-base md:text-lg xl:text-xl cursor-pointer text-jet"
                                            >
                                                Log in
                                            </Link>
                                        </p>
                                    </>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            {isOpen && <Modal setIsOpen={setIsOpen} modalText={modalText} />}
        </div>
    );
}

export default SignUp;
