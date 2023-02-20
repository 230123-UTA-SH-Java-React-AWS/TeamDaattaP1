import { useState } from "react";
import { AppBar, Button, Toolbar } from "react95";
import styled from "styled-components";
import { useAppDispatch, useAppSelector } from "../redux/hooks";
import {
  selectTheme,
  toggleThemeDark,
  toggleThemeLight,
} from "../features/darkMode/themeSlice";
import original from "react95/dist/themes/original";

function Navbar() {
  const [open, setOpen] = useState(false);

  //redux theme
  const theme = useAppSelector(selectTheme);
  const dispatch = useAppDispatch();

  return (
    <AppBar style={{ top: 0, bottom: 0, left: 0, width: 90 }}>
      <Toolbar style={{ justifyContent: "space-between" }}>
        <div style={{ position: "relative", display: "inline-block" }}>
          {!open && (
            <>
              <NavButton
                primary
                onClick={() => setOpen(!open)}
                active={open}
                style={{
                  fontWeight: "bold",
                }}
              >
                Log In
              </NavButton>
              <NavButton style={{ paddingLeft: 6, paddingRight: 6 }}>
                Sign up
              </NavButton>
            </>
          )}
          {open && (
            <>
              <NavButton
                primary
                onClick={() => setOpen(!open)}
                style={{
                  fontWeight: "bold",
                }}
              >
                Log Out
              </NavButton>
              <NavButton>Profile</NavButton>
              <NavButton>Posts</NavButton>
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
