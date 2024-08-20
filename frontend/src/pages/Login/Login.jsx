import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ReactComponent as LoginImg } from '../../assets/login.svg';
import axios from 'axios';
import Modal from "../../components/Modal/Modal";

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isOpen, setIsOpen] = useState(false);
    const [modalText, setModalText] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        const user = { email, password };

        try {
            const resp = await axios.post(
                'http://localhost:8080/user/login',
                user,
                {
                    withCredentials: true,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            console.log("Resp=" + JSON.stringify(resp));
            if (resp.data === 'Incorrect username/email or password') {
                setIsOpen(true);
                setModalText("Incorrect username/email or password. Please enter your credentials again.");
            } else {
                console.log("SUCC Resp=" + JSON.stringify(resp));
                localStorage.setItem('jwt', resp.data.jwt);
                localStorage.setItem('refreshToken', resp.data.refreshToken);

                navigate('/dashboard');
            }
        } catch (error) {
            console.log("Error= " + error.response);
        }
    };

    return (
        <div className="lg:grid lg:grid-cols-2 items-center h-screen overflow-x-hidden">
            <div className="hidden lg:flex left-side h-screen justify-center items-center">
                <div className="">
                    <LoginImg className="lg:p-20 xl:p-16" />
                </div>
            </div>
            <div className="h-full flex justify-center items-center bg-white">
                <div className="flex w-full justify-center">
                    <div className="w-full mt-20 md:flex md:justify-center md:items-center">
                        <div className="w-screen md:w-8/12 justify-center items-center font-josefin p-8 text-sm sm:text-base md:text-lg xl:text-xl text-jet">
                            <h1 className="font-josefin font-bold flex justify-center">
                                Welcome back :)
                            </h1>
                            <form onSubmit={handleLogin} action="submit">
                                <h1 className="">Email</h1>
                                <input
                                    type="text"
                                    autoFocus
                                    required
                                    className="bg-white w-full p-3 focus:outline-none focus:border-jet rounded-3xl border-4 border-jet"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                                <h1 className="pt-2">Password</h1>
                                <input
                                    type="password"
                                    required
                                    className="bg-white w-full p-3 focus:outline-none focus:border-jet rounded-3xl border-4 border-jet"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                                <p className="text-red-700 flex justify-center mt-2 font-josefin"></p>
                                <div className="btnContainer flex place-items-start justify-center mb-5">
                                    <button className="btn-auth-login text-sm sm:text-base md:text-lg xl:text-xl hover:bg-jet hover:text-white focus:outline-none">
                                        Log in
                                    </button>
                                </div>
                                <>
                                    <p className="">
                                        Don't have an account?
                                        <Link to="/signup" className="pl-2 cursor-pointer text-jet">
                                            Sign up
                                        </Link>
                                    </p>
                                </>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            {isOpen && <Modal setIsOpen={setIsOpen} modalText={modalText} />}
        </div>
    );
}

export default Login;
