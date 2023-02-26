import { useState } from "react";
import { Button, Frame, GroupBox, Tab, TabBody, Tabs, TextInput, Toolbar, Window, WindowContent, WindowHeader } from "react95";
import styled from "styled-components";

function Post() {
  const [state, setState] = useState({
    activeTab: 0,
  });

  const handleChange = (
    value: number,
    event: React.MouseEvent<HTMLButtonElement>
  ) => {
    setState({ activeTab: value });
  };
  const { activeTab } = state;

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
          <Tabs value={activeTab} onChange={handleChange}>
            <Tab value={0}>Post</Tab>
            <Tab value={1}>Comments</Tab>
          </Tabs>
          <TabBody style={{ height: 600 }}>
            {activeTab === 0 && (
              <p>
                Lorem ipsum dolor sit amet consectetur, adipisicing elit.
                Consectetur totam voluptates delectus mollitia expedita modi
                repudiandae ex deserunt voluptate, deleniti praesentium aliquid, id
                odio quam nobis ea nulla soluta porro?
              </p>
            )}
            {activeTab === 1 && (
              <GroupBox className="groupBox" label="User 1">
                <p>
                  Lorem ipsum dolor sit amet consectetur, adipisicing elit.
                  Consectetur totam voluptates delectus mollitia expedita modi
                  repudiandae ex deserunt voluptate, deleniti praesentium aliquid, id
                  odio quam nobis ea nulla soluta porro?
                </p>
              </GroupBox>
            )}
          </TabBody>
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
