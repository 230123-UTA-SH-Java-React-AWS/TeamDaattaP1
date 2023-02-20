import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "../../redux/store";
import { Theme } from "react95/dist/types";
import original from "react95/dist/themes/original";
import tokyoDark from "react95/dist/themes/tokyoDark";

export interface themeState {
  value: Theme;
}
const initialState: themeState = {
  value: original,
};

export const themeSlice = createSlice({
  name: "darkMode",
  initialState,
  reducers: {
    toggleThemeDark: (state) => {
      state.value = tokyoDark;
    },
    toggleThemeLight: (state) => {
      state.value = original;
    },
  },
});

export const { toggleThemeDark, toggleThemeLight } = themeSlice.actions;

export const selectTheme = (state: RootState) => state.darkMode.value;

export default themeSlice.reducer;
