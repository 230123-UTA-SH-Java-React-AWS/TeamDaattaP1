import { configureStore, ThunkAction, Action } from "@reduxjs/toolkit";
import counterReducer from "../features/counter/counterSlice";
import themeSlice from "../features/darkMode/themeSlice";
import authSlice, {
  loginWithToken,
} from "../features/authentication/authSlice";
import postSlice from "../features/postfeed/postSlice";

export const store = configureStore({
  reducer: {
    counter: counterReducer,
    darkMode: themeSlice,
    auth: authSlice,
    postList: postSlice,
  },
});

// Set token in store when app loads and log user in
const token = localStorage.getItem("token");
if (token) {
  store.dispatch(loginWithToken);
}

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
