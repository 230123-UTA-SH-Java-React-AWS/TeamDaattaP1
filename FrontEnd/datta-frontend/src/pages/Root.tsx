import { ThemeProvider, createGlobalStyle } from "styled-components";
/* Original Windows95 font (optional) */
// uses fonts.d.ts file to declare the fonts as modules for typescript
import ms_sans_serif from "react95/dist/fonts/ms_sans_serif.woff2";
import ms_sans_serif_bold from "react95/dist/fonts/ms_sans_serif_bold.woff2";
import Navbar from "../components/Navbar";
import { selectTheme } from "../features/darkMode/themeSlice";
import { useAppSelector } from "../redux/hooks";
import { Outlet } from "react-router-dom";
import "../App.css";
import Helmet from "react-helmet";
import original from "react95/dist/themes/original";

// WIN 95 styles
const GlobalStyles = createGlobalStyle`
  @font-face {
    font-family: 'ms_sans_serif';
    src: url('${ms_sans_serif}') format('woff2');
    font-weight: 400;
    font-style: normal
  }
  @font-face {
    font-family: 'ms_sans_serif';
    src: url('${ms_sans_serif_bold}') format('woff2');
    font-weight: bold;
    font-style: normal
  }
  body, input, select, textarea {
    font-family: 'ms_sans_serif';
  }
`;
function Root() {
  const theme = useAppSelector(selectTheme);
  const bodyStyle =
    theme == original
      ? "background-color : #fff"
      : "background-color : rgb(24, 26, 27)";

  return (
    <>
      <GlobalStyles />
      <ThemeProvider theme={theme}>
        <Helmet bodyAttributes={{ style: bodyStyle }} />
        <Navbar />
        <div className="App">
          <Outlet />
        </div>
      </ThemeProvider>
    </>
  );
}

export default Root;
