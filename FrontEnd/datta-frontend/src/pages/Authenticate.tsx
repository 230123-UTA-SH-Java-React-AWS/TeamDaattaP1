import { useState } from "react";
import { useForm, useWatch } from "react-hook-form";
import {
  Button,
  Frame,
  GroupBox,
  Tab,
  TabBody,
  Tabs,
  TextInput,
  Toolbar,
  Window,
  WindowContent,
  WindowHeader,
} from "react95";
import styled from "styled-components";
import {
  loginAsync,
  loginFailure,
  loginSuccess,
  registerAsync,
  registerFailure,
  registerSuccess,
} from "../features/authentication/authSlice";
import { useAppDispatch, useAppSelector } from "../redux/hooks";
import { RootState } from "../redux/store";
import { useNavigate } from "react-router";

function Authenticate() {
  //redux
  const dispatch = useAppDispatch();

  //show the correct tab (login, register)
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

  // react-hook-forms stuff
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const navigate = useNavigate();
  const onSubmitLogin = async (data: any) => {
    console.log(data);
    try {
      const { user, token } = await dispatch(loginAsync(data)).unwrap();
      dispatch(loginSuccess({ user, token }));
      navigate("/");
    } catch (error) {
      dispatch(loginFailure(error as string));
    }
  };
  const onSubmitRegister = async (data: any) => {
    console.log(data);
    try {
      const { user, token } = await dispatch(registerAsync(data)).unwrap();
      dispatch(registerSuccess({ user, token }));
      navigate("/");
    } catch (error) {
      dispatch(registerFailure(error as string));
    }
  };

  // const auth = useAppSelector((state: RootState) => state.auth);

  // if (auth.isAuthenticated) {
  //   console.log(auth.user);
  //   // Access the user data, for example, auth.user.name or auth.user.email
  // }

  return (
    <AuthWindow>
      <Window resizable className="window">
        <WindowHeader className="window-title">
          <span>User</span>
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
            <Tab value={0}>Login</Tab>
            <Tab value={1}>Register</Tab>
          </Tabs>
          <TabBody style={{ height: 600 }}>
            {activeTab === 0 && (
              <form>
                <GroupBox className="groupBox" label="Email">
                  <TextInput
                    placeholder="Type email here..."
                    fullWidth
                    type="email"
                    {...register("email", {
                      required: true,
                      pattern: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                    })}
                  />
                  {errors.email && (
                    <p>Email address not valid. example@gmail.com</p>
                  )}
                </GroupBox>

                <GroupBox className="groupBox" label="Password">
                  <TextInput
                    placeholder="Type password here..."
                    fullWidth
                    type="password"
                    {...register("password", {
                      required: true,
                      minLength: {
                        value: 8,
                        message: "Password must be at least 8 characters long",
                      },
                      pattern: {
                        value: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
                        message:
                          "Password must contain at least one letter and one number",
                      },
                    })}
                  />
                  {errors.password && (
                    <p>
                      Your password must be at least 8 characters long and must
                      contain atleast 1 number
                    </p>
                  )}
                </GroupBox>
                <Button
                  size="lg"
                  className="form-btn"
                  onClick={handleSubmit(onSubmitLogin)}
                >
                  Submit
                </Button>
              </form>
            )}
            {activeTab === 1 && (
              <form>
                <GroupBox className="groupBox" label="Email">
                  <TextInput
                    placeholder="Type email here..."
                    fullWidth
                    type="email"
                    {...register("email", {
                      required: true,
                      pattern: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                    })}
                  />
                  {errors.email && (
                    <p>Email address is not valid. example@gmail.com</p>
                  )}
                </GroupBox>

                <GroupBox className="groupBox" label="Password">
                  <TextInput
                    placeholder="Type password here..."
                    fullWidth
                    type="password"
                    {...register("password", {
                      required: true,
                      minLength: {
                        value: 8,
                        message: "Password must be at least 8 characters long",
                      },
                      pattern: {
                        value: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
                        message:
                          "Password must contain at least one letter and one number",
                      },
                    })}
                  />
                  {errors.password && (
                    <p>
                      Your password must be at least 8 characters long and must
                      contain atleast 1 number
                    </p>
                  )}
                </GroupBox>

                <Button
                  size="lg"
                  className="form-btn"
                  onClick={handleSubmit(onSubmitRegister)}
                >
                  Submit
                </Button>
              </form>
            )}
          </TabBody>
        </WindowContent>
      </Window>
    </AuthWindow>
  );
}

export default Authenticate;

const AuthWindow = styled.div`
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
