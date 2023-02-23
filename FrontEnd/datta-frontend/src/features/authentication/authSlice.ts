import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { getToken } from "../../util/getToken";

interface User {
  id: string;
  name: string;
  email: string;
}

interface AuthState {
  isAuthenticated: boolean;
  user: User | null;
  error: string | null;
  token: string;
}

interface LoginPayload {
  email: string;
  password: string;
}

interface RegisterPayload {
  email: string;
  password: string;
}

const initialState: AuthState = {
  isAuthenticated: !!getToken(),
  user: null,
  error: null,
  token: "",
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loginSuccess: (
      state,
      action: PayloadAction<{ user: User; token: string }>
    ) => {
      state.isAuthenticated = true;
      state.user = action.payload.user;
      state.error = null;
      state.token = action.payload.token || "";
    },
    loginFailure: (state, action: PayloadAction<string>) => {
      state.isAuthenticated = false;
      state.user = null;
      state.error = action.payload;
      state.token = "";
    },
    logoutSuccess: (state) => {
      state.isAuthenticated = false;
      state.user = null;
      state.error = null;
      state.token = "";
      localStorage.removeItem("token");
    },
    loginWithToken: (state, action: PayloadAction<string>) => {
      state.isAuthenticated = true;
      state.error = null;
      state.token = action.payload;
    },
    registerSuccess: (
      state,
      action: PayloadAction<{ user: User; token: string }>
    ) => {
      state.isAuthenticated = true;
      state.user = action.payload.user;
      state.error = null;
      state.token = action.payload.token;
    },
    registerFailure: (state, action: PayloadAction<string>) => {
      state.isAuthenticated = false;
      state.user = null;
      state.error = action.payload;
      state.token = "";
    },
  },
  extraReducers: (builder) => {
    builder.addCase(loginAsync.fulfilled, (state, action) => {
      state.isAuthenticated = true;
      state.user = action.payload.user;
      state.error = null;
      state.token = action.payload.token || "";
    });
    builder.addCase(loginAsync.rejected, (state, action) => {
      state.isAuthenticated = false;
      state.user = null;
      state.error = action.error.message || "Something went wrong.";
      state.token = "";
    });
    builder.addCase(registerAsync.fulfilled, (state, action) => {
      state.isAuthenticated = true;
      state.user = action.payload.user;
      state.error = null;
      state.token = action.payload.token || "";
    });
    builder.addCase(registerAsync.rejected, (state, action) => {
      state.isAuthenticated = false;
      state.user = null;
      state.error = action.error.message || "Something went wrong.";
      state.token = "";
    });
  },
});

export const {
  loginSuccess,
  loginFailure,
  logoutSuccess,
  loginWithToken,
  registerFailure,
  registerSuccess,
} = authSlice.actions;

export default authSlice.reducer;

//========LOGIN============
export const loginAsync = createAsyncThunk(
  "auth/login",
  async (loginPayload: LoginPayload) => {
    const response = await fetch("/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(loginPayload),
    });
    if (response.ok) {
      const data = await response.json();
      const user = data.user;
      const token = response.headers.get("Authorization") || "";
      // Save the token to localStorage
      if (token) {
        localStorage.setItem("token", token);
      }
      console.log("login post was successfull");

      return { user, token };
    } else {
      console.log("failed to log in");

      throw new Error("Failed to log in");
    }
  }
);

//=========REGISTER=========
export const registerAsync = createAsyncThunk(
  "auth/register",
  async (registerPayload: RegisterPayload) => {
    const response = await fetch("/api/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(registerPayload),
    });
    if (response.ok) {
      const data = await response.json();
      const user = data.user;
      const token = response.headers.get("Authorization") || "";
      // Save the token to localStorage
      if (token) {
        localStorage.setItem("token", token);
      }
      return { user, token };
    } else {
      throw new Error("Failed to register");
    }
  }
);
