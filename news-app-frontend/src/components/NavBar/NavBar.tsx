import { NavLink } from "react-router-dom";
import styles from "./NavBar.module.scss"
import { useNavigate } from "react-router-dom";

export function NavBar() {

    const navigate = useNavigate();
    function navigateToHome() {
        navigate("/");
        window.scrollTo(0, 0);
    }

    return (
        <div className={styles.navbar}>
            <div className={styles.navbar__logo}>
                <h1 className={styles.logo} onClick={navigateToHome}>Uutissivusto</h1>
                <NavLink
                    to="/"
                    className={({ isActive }) => (isActive ? styles.active : "")}
                >
                    koti
                </NavLink>
                <NavLink
                    to="/source/all"
                    className={({ isActive }) => (isActive ? styles.active : "")}
                >
                    Kaikki uutiset
                </NavLink>
                <NavLink
                    to="/source/yle"
                    className={({ isActive }) => (isActive ? styles.active : "")}
                >
                    Yle Uutiset
                </NavLink>
            </div>

        </div>
    );
}





