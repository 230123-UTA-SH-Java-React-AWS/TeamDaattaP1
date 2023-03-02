import { useEffect, useState } from "react";
import {
  getPostsAsync,
  postListLoadFailure,
  postListLoadSuccess,
} from "../features/postfeed/postSlice";
import { useAppDispatch, useAppSelector } from "../redux/hooks";
//import { RootState } from "../redux/store";
import Post from "./Post";

function PostFeed() {
  interface Post {
    userName: string;
    content: string;
    id: number;
    liked: boolean;
    userID: number;
  }

  const dispatch = useAppDispatch();
  const [state, setState] = useState({
    postList: [
      {
        userName: "",
        content: "",
        id: 0,
        liked: false,
        userID: -1,
      },
    ],
  });

  const [error, setError] = useState("");

  const loadAllPosts = async () => {
    setError("");

    try {
      const { postListObject } = await dispatch(getPostsAsync()).unwrap();
      dispatch(postListLoadSuccess(postListObject));
      const postList: Post[] = Object.values(postListObject);
      setState({ postList });
    } catch (error: any) {
      dispatch(postListLoadFailure(error));
      setError(error.message);
    }
  };

  // const postFeed = useAppSelector((state: RootState) => state.postList);

  useEffect(() => {
    loadAllPosts();
  }, []);

  return (
    <>
      {error.length > 0 && <p>{error}</p>}
      {state.postList
        .slice(0)
        .reverse()
        .map((p) => (
          <Post
            key={p.id}
            userID={p.userID}
            content={p.content}
            userName={p.userName}
          />
        ))}
    </>
  );
}

export default PostFeed;
