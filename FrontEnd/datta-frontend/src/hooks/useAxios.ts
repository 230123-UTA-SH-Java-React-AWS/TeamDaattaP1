//hook to fetch data using axios

import axios, { AxiosRequestConfig } from "axios";
import { useEffect, useState } from "react";

//config is the http method, url, and other optional parameters
// loadOnStart determines if axios should fetch data immediately when started - automatically true
// hook returns a boolean loading state, T or undefined data, string error, request function
// T is a generic to be replaced with whatever data type
export const useAxios = <T>(
  config: AxiosRequestConfig<any>,
  loadOnStart: boolean = true
): [boolean, T | undefined, string, () => void] => {
  // base states, loading determines if data has been fetched, error for any http errors, data will contain the fetched response
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState<T>();
  const [error, setError] = useState("");

  useEffect(() => {
    if (loadOnStart) sendRequest();
  }, []);

  // request function provided if loadOnStart is false
  const request = () => {
    sendRequest();
  };

  const sendRequest = () => {
    setLoading(true);

    axios(config)
      .then((response) => {
        // if response was successfull, set to no error and set data to the response
        setError("");
        setData(response.data); //make sure fetched data has a data array/object // if not then delete this .data or switch to the name of fetched data
      })
      .catch((error) => {
        // if there was an error, set the message of it
        setError(error.message);
      })
      .finally(() => {
        // once data/error is set then no longer loading
        setLoading(false);
      });
  };

  return [loading, data, error, request];
};
