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
        postList: [{
            userName: "",
            content: "",
            id: 0,
            liked: false,
            userID: -1
        }]
    });

  const loadAllPosts = async () => {
    try {
      const { postListObject } = await dispatch(getPostsAsync()).unwrap();
      dispatch(postListLoadSuccess(postListObject));
      const postList: Post[] = Object.values(postListObject);
      setState({ postList });
    } catch (error) {
      dispatch(postListLoadFailure(error as string));
    }
  };
    
    // const postFeed = useAppSelector((state: RootState) => state.postList);

    useEffect( () => {
        loadAllPosts();
    }, []);
    
    return (
      <>
        { 
          state.postList.map((p) => (
            <Post key={p.id} userID={p.userID} content={p.content} userName={p.userName}/>
          ))
        }
      </>
    );
}

export default PostFeed;
