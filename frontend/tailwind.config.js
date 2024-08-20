module.exports = {
    mode: 'jit',
    // content: [
    //     './src/**/*.{html,js}',
    //     './node_modules/tw-elements/dist/js/**/*.js',
    // ],
    purge: [
        './public/**/*.html',
        './src/**/*.{js,jsx,ts,tsx,vue}',
        './src/components/**/*.{js,jsx,ts,tsx,vue}',
    ],
    theme: {
        extend: {
            colors: {
                login: 'hsl(10, 80%, 74%)',
                snow: 'hsl(348, 45%, 97%)',
                jet: 'hsl(0, 0%, 20%)',
                form: 'hsl(10, 56%, 73%)',
                link: 'hsl(5, 87%, 69%)',
                card: 'hsl(0, 0%, 90.19607843137256%)',
                desk: '#9EA8B3',
                cardBullet: 'hsl(0, 0%, 76%)',
                coursesBullet: 'hsl(152, 36%, 69%)',
                seminarsBullet: 'hsl(218, 51%, 54%)',
                labsBullet: 'hsl(47, 100%, 83%)',
                veryEasy: 'hsl(207, 82%, 82%)',
                easy: 'hsl(152, 36%, 69%)',
                medium: 'hsl(16, 85%, 65%)',
                hard: 'hsl(355, 89%, 51%)',
                db: 'hsl(231, 100%, 33%)',
                // dbWhite: '',
            },

            boxShadow: {
                card: '1px 6px 23px 9px rgba(0,0,0,0.25)',
            },
            fontFamily: {
                josefin: ['"Josefin Sans"'],
            },
        },
        screens: {
            sm: '640px',
            md: '768px',
            lg: '1024px',
            xl: '1280px',
            '2xl': '1536px',
            msm: { max: '640px' },
            mmd: { max: '768px' },
            mlg: { max: '1024px' },
            mxl: { max: '1280px' },
            m2xl: { max: '1536px' },
        },
    },
};
