import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit";

interface PostListObject {
    [postID: number]: Post;
}

interface Post {
    username: string;
    content: string;
    id: number;
    liked: boolean;
    userID: number;
}

interface PostState {
    postListObject: PostListObject | null;
    error: string | null
}

const initialState: PostState = {
    postListObject: null,
    error: null
}

const postSlice = createSlice({
    name: "post",
    initialState,
    reducers: {
        postListLoadSuccess: (
            state,
            action: PayloadAction<{postListObject: PostListObject}>
        ) => {
            state.postListObject = action.payload.postListObject;
            state.error = null;
        },
        postListLoadFailure: (state, action: PayloadAction<string>) => {
            state.postListObject = null;
            state.error = action.payload;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(getPostsAsync.fulfilled, (state, action) => {
            state.postListObject = action.payload;
            state.error = null;
        });
        builder.addCase(getPostsAsync.rejected, (state, action) => {
            state.postListObject = null;
            state.error = action.error.message || "Something went wrong.";
        });
    }
});

export const {
    postListLoadSuccess,
    postListLoadFailure
} = postSlice.actions;

export default postSlice.reducer;

export const getPostsAsync = createAsyncThunk(
    "/post",
    async () => {
        const response = await fetch("http://localhost:8000/post", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });
        
        if(response.ok) {
            const postListObject = await response.json();

            return { postListObject };
        } else {
            throw new Error("Failed to load Post Feed");
        }
    }
)

// const [loading, data, error, request] = useAxios<PostListObject>({
//     method: "GET",
//     url: "http://localhost:8000/post"
//   })

//   if(loading) return <p>Loading...</p>;
//   if(error !== "") return <p>{error}</p>;
//   if(!data) return <p>Data was null</p>;

//  const data2 = Object.values(data);