import React from 'react';
import { AiFillHome } from 'react-icons/ai';
import { IoIosStats } from 'react-icons/io';
import { FiSettings } from 'react-icons/fi';

export const SidebarData = [
    {
        title: 'Home',
        path: '/',
        icon: <AiFillHome />,
        cName: 'nav-text',
    },
    {
        title: 'Dashboard',
        path: '/dashboard',
        icon: <IoIosStats />,
        cName: 'nav-text',
    },

    {
        title: 'Settings',
        path: '/settings',
        icon: <FiSettings />,
        cName: 'nav-text',
    },
];
