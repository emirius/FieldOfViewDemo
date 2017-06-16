# FieldOfViewDemo
## 1. Introduction
This is a simple demo application that contains a basic "field of view"-algorithm for a 2D tilemap where all the tiles are supposed to be squares of the same size.
The algorithm iterates over all tiles and checks whether or not they are visible (i.e. is a tile inbetween them and a viewpoint that would block the view). This 
check works by defining a line between the viewpoint and the tile to check and calculating the distance between this line and possible view-blocking tiles.
If this distance is smaller than half of the a tile's square size (thus basically checking the inner circle of a tile, rather the the tile itself) then the view is blocked.

## 2. Application
Once the application gets started a window (JFrame) will open that shows the current tilemap:

* Visible tiles are **green**
* Tiles can not be seen are **red**
* Tiles that block the view are **black**
* The viewpoint is **blue**

### 2.1 Interaction
Use the mouse to interact with the application:

* **Left mouse button:** Toggle the tile's blocking-view property (i.e. whether or not it will block the view of tiles behind it).
* **Right mouse button:** Set the clicked tile to be the viewpoint (can not be used on a blocking-view tile).
