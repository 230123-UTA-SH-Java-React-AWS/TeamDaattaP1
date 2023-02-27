import { useEffect, useState } from "react";
import { AppBar, Button, Toolbar } from "react95";
import styled from "styled-components";
import { useAppDispatch, useAppSelector } from "../redux/hooks";
import {
  selectTheme,
  toggleThemeDark,
  toggleThemeLight,
} from "../features/darkMode/themeSlice";
import original from "react95/dist/themes/original";
import { Link, useNavigate } from "react-router-dom";
import { RootState } from "../redux/store";
import { logoutSuccess } from "../features/authentication/authSlice";

function Navbar() {
  //toggles profile and posts buttons once login is clicked
  const auth = useAppSelector((state: RootState) => state.auth);
  console.log(auth);

  //redux theme
  const theme = useAppSelector(selectTheme);
  const dispatch = useAppDispatch();

  //redirect user when it logs out
  const navigate = useNavigate();

  //logout user
  const handleLogout = () => {
    dispatch(logoutSuccess());
    navigate("/login");
  };

  return (
    <AppBar style={{ top: 0, bottom: 0, left: 0, width: 90 }}>
      <Toolbar style={{ justifyContent: "space-between" }}>
        <div style={{ position: "relative", display: "inline-block" }}>
          {!auth.isAuthenticated && (
            <>
              <Link to={"/login"}>
                <NavButton
                  primary
                  style={{
                    fontWeight: "bold",
                  }}
                >
                  Log In
                </NavButton>
              </Link>
            </>
          )}
          {auth.isAuthenticated && (
            <>
              <Link to={"/"}>
                <NavButton
                  primary
                  onClick={handleLogout}
                  style={{
                    fontWeight: "bold",
                  }}
                >
                  Log Out
                </NavButton>
              </Link>
              <Link to={"/profile"}>
                <NavButton>Profile</NavButton>
              </Link>
            </>
          )}
          {theme == original ? (
            <NavButton onClick={() => dispatch(toggleThemeDark())}>
              Light
            </NavButton>
          ) : (
            <NavButton onClick={() => dispatch(toggleThemeLight())}>
              Dark
            </NavButton>
          )}
        </div>
      </Toolbar>
    </AppBar>
  );
}

export default Navbar;

const NavButton = styled(Button)`
  margin-bottom: 20px;
  width: 75px;
  height: 75px;
`;
