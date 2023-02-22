import { Button, Toolbar, Window, WindowContent, WindowHeader } from "react95";
import styled from "styled-components";

function Post() {
  return (
    <>
      <PostWindow resizable className="window" style={{ marginBottom: 20 }}>
        <WindowHeader className="window-title">
          <span>Example Post Title</span>
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
          <p>
            Lorem ipsum dolor sit amet consectetur, adipisicing elit.
            Consectetur totam voluptates delectus mollitia expedita modi
            repudiandae ex deserunt voluptate, deleniti praesentium aliquid, id
            odio quam nobis ea nulla soluta porro?
          </p>
        </WindowContent>
      </PostWindow>
    </>
  );
}

export default Post;

const PostWindow = styled(Window)`
  .window-title {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
`;
