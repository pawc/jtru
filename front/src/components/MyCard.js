import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea, Grid } from '@mui/material';
import ThumbUpOffAlt from '@mui/icons-material/ThumbUpOffAlt';
import ThumbUpAlt from '@mui/icons-material/ThumbUpAlt';
import configData from '../config.json'

class MyCard extends React.Component {

  constructor(props){
    super(props)
    this.state = {
      isFav : false
    }
  }

  toggleLike(type, key){

    this.setState({
      isFav : !this.state.isFav
    })

    let body =  {
      type: type,
      key: key
    }
  
    fetch(configData.SERVER_URL + '/toggle', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include',
      body: JSON.stringify(body)
    })
  }

  render(){
    return (
      <Card sx={{ maxWidth: 345, margin: 5, display: 'inline-block'}}>
        <CardActionArea>
          <CardMedia
            component="img"
            height="140"
            image={this.props.img_src}
          />
          <CardContent>
            <Typography  align="center" variant="h6" component="div">
              {this.props.title}
            </Typography>
            <Typography variant="subtitle1"  align="center" color="text.secondary">
              {this.props.artists}
            </Typography>
              <Grid container spacing={2}>
                <Grid item xs={4}>
                  <Typography align="center" variant="subtitle2" color="text.secondary">
                    {this.props.year}
                  </Typography>
                </Grid>
                <Grid item xs={4}>
                  <Typography align="center" variant="subtitle2" color="text.secondary">
                    {this.props.itemType}
                  </Typography>
                </Grid>
                <Grid item xs={4}>
                  <Typography align="right">
                    { !this.state.isFav &&
                      <ThumbUpOffAlt sx={{color: "#1f6933"}} onClick={() => this.toggleLike(this.props.itemType, this.props.itemKey)}/>
                    }
                    { this.state.isFav &&
                      <ThumbUpAlt sx={{color: "#1f6933"}} onClick={() => this.toggleLike(this.props.itemType, this.props.itemKey)}/>
                    }
                  </Typography>
                </Grid>
              </Grid>
          </CardContent>
        </CardActionArea>
      </Card>
    );
  }
}

export default MyCard