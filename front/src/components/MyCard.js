import React, {useState} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea, Grid } from '@mui/material';
import ThumbUpOffAlt from '@mui/icons-material/ThumbUpOffAlt';
import ThumbUpAlt from '@mui/icons-material/ThumbUpAlt';
import configData from '../config.json'

export default function MyCard(props) {

  const [isFav, setFav] = useState(props.fav)

  const toggleLike = (type, key, artists, title, img_src, year) => {

    setFav(!isFav)

    let body =  {
      id: 0,
      user: {
        id: 0,
        email: null,
        password: null
      },
      item: {
        itemKey: key,
        type: type,
        artists: artists,
        title: title,
        img_src: img_src,
        year: year
      }
    }
  
    fetch(configData.SERVER_URL + '/toggle', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include',
      body: JSON.stringify(body)
    })

    props.unFav(props.itemKey)

  }

  return (
    <Card sx={{ maxWidth: 345, margin: 5, display: 'inline-block'}}>
      <CardActionArea>
        <CardMedia
          component="img"
          height="140"
          image={props.img_src}
        />
        <CardContent>
          <Typography  align="center" variant="h6" component="div">
            {props.title}
          </Typography>
          <Typography variant="subtitle1"  align="center" color="text.secondary">
            {props.artists}
          </Typography>
            <Grid container spacing={2}>
              <Grid item xs={4}>
                <Typography align="center" variant="subtitle2" color="text.secondary">
                  {props.year}
                </Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography align="center" variant="subtitle2" color="text.secondary">
                  {props.itemType}
                </Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography align="right">
                  { !isFav &&
                    <ThumbUpOffAlt sx={{color: "#1f6933"}} onClick={() => toggleLike(props.itemType, 
                      props.itemKey, props.artists, props.title, props.img_src, props.year
                      )}/>
                  }
                  { isFav &&
                    <ThumbUpAlt sx={{color: "#1f6933"}} onClick={() => toggleLike(props.itemType, props.itemKey)}/>
                  }
                </Typography>
              </Grid>
            </Grid>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}