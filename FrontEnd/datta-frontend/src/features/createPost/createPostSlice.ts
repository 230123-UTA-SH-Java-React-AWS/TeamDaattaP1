import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit";

interface PostPayload {
    userID: number;
    content: string;
}

interface PostState {
    postid: number | null,
    error: string | null
}

const initialState:PostState = {
    postid: 0,
    error: null
}

const createPostSlice = createSlice({
    name: "post",
    initialState,
    reducers: {
        postCreateSuccess: (
            state,
            action: PayloadAction<{postid: number}>
        ) => {
            state.postid = action.payload.postid;
            state.error = null;
        },
        postCreateFailure: (state, action: PayloadAction<string>) => {
            state.postid = null;
            state.error = action.payload;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(postCreateAsync.fulfilled, (state, action) => {
            state.postid = action.payload;
            state.error = null;
        });
        builder.addCase(postCreateAsync.rejected, (state, action) => {
            state.postid = null;
            state.error = action.error.message || "Something went wrong.";
        });
    }
});

export const {
    postCreateSuccess,
    postCreateFailure
} = createPostSlice.actions;

export default createPostSlice.reducer;

export const postCreateAsync = createAsyncThunk(
    "/post",
    async (postPayload: PostPayload) => {
        const response = await fetch("http://localhost:8000/post", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(postPayload)
        });
        console.log(response.headers);

        if(response.ok) {
            const postid = await response.json();

            return postid;
        } else {
            console.log("failed to create new post");
            
            throw new Error("Failed to create new Post");
        }
    }
)