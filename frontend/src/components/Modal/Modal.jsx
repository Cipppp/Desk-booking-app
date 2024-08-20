import React from "react";
import styles from "./Modal.module.css";
import { RiCloseLine } from "react-icons/ri";

const Modal = ({ setIsOpen, modalText }) => {
    return (
        <>
            <div className={styles.darkBG} onClick={() => setIsOpen(false)} />
            <div className={styles.centered}>
                <div className={styles.modal}>
                    <div className={styles.modalHeader}>
                        <h5 className={styles.heading}>Oops..</h5>
                    </div>
                    <button className={styles.closeBtn} onClick={() => setIsOpen(false)}>
                        <RiCloseLine style={{ marginBottom: "-3px" }} />
                    </button>
                    <div className={styles.modalContent}>
                        {modalText}
                    </div>
                </div>
            </div>
        </>
    );
};

export default Modal;
