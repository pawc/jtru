import React from 'react';
import {
  Grid,
  TextField,
  Paper,
  Button,
  Typography
} from '@mui/material';
import configData from '../config.json';

const LoginForm = (props) => {

  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [errorText, setErrorText] = React.useState("");
  const [successText, setSuccessText] = React.useState("");

  const handleLogin = () => {

    let base64 = require('base-64');

    fetch(configData.SERVER_URL + '/login', {
      method: "POST",
      credentials: 'include',
      headers: {
        'Authorization': 'Basic ' + base64.encode(username + ":" + password)
      }
    })
    .then((response) => {
      if(!response.ok){
        setErrorText("Invalid credentials")
      }
      props.loginCallback(response.ok)
    })
  }

  const handleRegister = () => {

    let user = {
      id: 0,
      email: username,
      password: password
    }

    fetch(configData.SERVER_URL + "/register", {
      method: "POST",
      body: JSON.stringify(user),
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then((response) => {
        if(response.ok){
          setErrorText('')
          setSuccessText("Registered successfully. You can now log in.")
        }
        else{
          setErrorText("Credentials too short or user already exists")
        }
    })
  }

  return (
    <div style={{ padding: 30 }}>
      <form>
      <Paper>
        <Grid
          container
          spacing={3}
          direction={'column'}
          justify={'center'}
          alignItems={'center'}
        >
          <Grid item xs={12}>
            <TextField label="e-mail" onChange={(e) => setUsername(e.target.value)}></TextField>
          </Grid>
          <Grid item xs={12}>
            <TextField label="password" onChange={(e) => setPassword(e.target.value)} type={'password'}></TextField>
          </Grid>
          <Grid item xs={12}>
            { errorText !== '' &&
            <Typography sx={{color: 'red'}}>{errorText}</Typography>
            }
            { successText !== '' &&
            <Typography sx={{color: 'green'}}>{successText}</Typography>
            }
          </Grid>
          <Grid item xs={6}>
            <Button fullWidth onClick={handleLogin}> Login </Button>
          </Grid>
          <Grid item xs={6}>
            <Button fullWidth onClick={handleRegister}> Register </Button>
          </Grid>
        </Grid>
      </Paper>
      </form>
    </div>
  );
};

export default LoginForm;