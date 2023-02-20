import "./App.css";
import Navbar from "./components/Navbar";
import { useAxios } from "./hooks/useAxios";
// WIN95/STYLED COMPONENTS
import { createGlobalStyle, ThemeProvider } from "styled-components";

/* Original Windows95 font (optional) */
// uses fonts.d.ts file to declare the fonts as modules for typescript
import ms_sans_serif from "react95/dist/fonts/ms_sans_serif.woff2";
import ms_sans_serif_bold from "react95/dist/fonts/ms_sans_serif_bold.woff2";
import { useAppSelector } from "./redux/hooks";
import { selectTheme } from "./features/darkMode/themeSlice";
import Post from "./components/Post";

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

export interface testPokeApiResponse {
  count: number;
  next: string;
  previous: null;
  results: testPokeData[];
}
export interface testPokeData {
  name: string;
  url: string;
}

function App() {
  const theme = useAppSelector(selectTheme);

  //testing useAxios hook to fetch data
  // const [loading, data, error, request] = useAxios<testPokeApiResponse>({
  //   method: "GET",
  //   url: "https://pokeapi.co/api/v2/pokemon?limit=10&offset=0",
  // });
  // console.log(data);
  // if (loading) return <p>Loading...</p>;
  // if (error !== "") return <p>{error}</p>;
  // if (!data) return <p>Data was null</p>;

  return (
    <div className="App">
      <GlobalStyles />
      <ThemeProvider theme={theme}>
        <Navbar />
        <Post />
        <Post />

        {/* {data.results.map((p) => (
          <h3 key={p.name}>{p.name}</h3>
        ))} */}
      </ThemeProvider>
    </div>
  );
}

export default App;
