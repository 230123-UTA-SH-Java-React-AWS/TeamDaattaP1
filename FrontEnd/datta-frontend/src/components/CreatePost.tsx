import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { Button, GroupBox, TextInput, Toolbar, Window, WindowContent, WindowHeader } from "react95";
import styled from "styled-components";
import { postCreateAsync, postCreateFailure, postCreateSuccess } from "../features/createPost/createPostSlice";
import { useAppDispatch } from "../redux/hooks";
//import { RootState } from "../redux/store";


function CreatePost() {
    const dispatch = useAppDispatch();
    
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();
    
    const navigate = useNavigate();
    const onSubmitPost = async (data: any) => {
        console.log(data);
    
        try{
            const {postid} = await dispatch(postCreateAsync(data)).unwrap();
            dispatch(postCreateSuccess({postid}))
            navigate("/");
        } catch(error) {
            dispatch(postCreateFailure(error as string));
        }
    }
    
    //const createPost = useAppSelector((state:RootState) => state.postList);
    return (
        <PostWindow>
            <Window resizable className="window" style={{ marginBottom: 20 }}>
                <WindowHeader className="window-title">
                <span>New Post</span>
                <Button>&#10006;</Button>
                </WindowHeader>
                <Toolbar>
                <Button variant="menu" size="sm">
                    File
                </Button>
                <Button variant="menu" size="sm">
                    Edit
                </Button>
                <Button variant="menu" size="sm" disabled>
                    Save
                </Button>
                </Toolbar>
                <WindowContent>
                
                
                <form>
                    <GroupBox className="groupBox" label="User's new post">
                    
                    {/* Not sure how to make this bigger */}
                    <TextInput
                        placeholder="New post..."
                        fullWidth
                        type="textarea"
                        {...register("content", {
                        required: true,
                        })}
                    />
                    </GroupBox>

                    <Button
                    size="lg"
                    className="form-btn"
                    onClick={handleSubmit(onSubmitPost)}
                    >
                    Submit
                    </Button>
                </form>
                
                </WindowContent>
            </Window>
        </PostWindow>
    )
}

export default CreatePost;

const PostWindow = styled.div`
  .window {
    width: 600px;
    height: 800px;
    margin: 2rem;
  }
  .window-title {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .form-btn {
    margin: 1rem auto 0;
  }
  .groupBox {
    margin-top: 2rem;
  }
  .submit-btn {
    border: none;
    background: none;
    font: inherit;
  }
`;