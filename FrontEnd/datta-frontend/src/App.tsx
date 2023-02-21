import "./App.css";
import { useAxios } from "./hooks/useAxios";

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
  // testing useAxios hook to fetch data
  const [loading, data, error, request] = useAxios<testPokeApiResponse>({
    method: "GET",
    url: "https://pokeapi.co/api/v2/pokemon?limit=10&offset=0",
  });
  console.log(data);
  if (loading) return <p>Loading...</p>;
  if (error !== "") return <p>{error}</p>;
  if (!data) return <p>Data was null</p>;

  return (
    <div className="App">
      {data.results.map((p) => (
        <h3 key={p.name}>{p.name}</h3>
      ))}
    </div>
  );
}

export default App;
