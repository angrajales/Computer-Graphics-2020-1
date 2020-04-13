#include "colors.inc"  
#include "metals.inc"
#include "stones.inc"
#include "woods.inc"

global_settings { ambient_light White } 

plane {
  <0,0,1>, 0           //This represents the plane 0x+0y+z=0
  texture { T_Stone24 }  //The texture comes from the file "metals.inc"
}


sphere {
    <0,0,.5>,.5
    pigment { Red }
    finish {
      ambient 0.9
      diffuse 0.1
      phong 0.9
      phong_size 10
    }
}

sphere {
    <1,0,.5>,.5
    pigment { Green }
    finish {
      ambient 0.5
      diffuse 0.3
      phong .8
      phong_size 100
    }
}

sphere {
    <2,0,.5>,.5
    finish {
      ambient 0.1
      diffuse 0.6
      phong .75
      phong_size 1000
    }
}
 

light_source {
  <0,0,10>              //Change this if you want to put the light at a different point
  color White*2        //Multiplying by 2 doubles the brightness
}

background { color White }
#declare mirror1=plane {        
  <-1,1,0>, 1                   
  texture {T_Silver_1C}
};    

 

//-x-y+0z=0 translated 1 unit in the direction <-1,-1,0>
#declare mirror2=plane {        
  <-1,-1,0>, 1                  
  texture {T_Silver_1C}
};


mirror1
mirror2

camera {
  sky <0,0,1>          //Don't change this
  direction <-1,0,0>   //Don't change this  
  right <-4/3,0,0>     //Don't change this
  location  <10,0,10>  //Camera location
  look_at   <0,0,0>    //Where camera is pointing
  angle 30      //Angle of the view--increase to see more, decrease to see less  
}