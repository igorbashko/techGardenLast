/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package techgarden;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationService;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.service.elevation.LocationElevationRequest;
import com.lynden.gmapsfx.service.elevation.PathElevationRequest;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.ArcBuilder;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import com.lynden.gmapsfx.shapes.Polygon;
import com.lynden.gmapsfx.shapes.PolygonOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import com.lynden.gmapsfx.shapes.Rectangle;
import com.lynden.gmapsfx.shapes.RectangleOptions;
import com.lynden.gmapsfx.zoom.MaxZoomResult;
import com.lynden.gmapsfx.zoom.MaxZoomService;
import com.lynden.gmapsfx.zoom.MaxZoomServiceCallback;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import java.time.LocalDate;

/**
 *
 * @author igor
 */
public class TechGarden extends Application implements MapComponentInitializedListener {
protected GoogleMapView mapComponent;
protected GoogleMap map;
/*private Button btnZoomIn;
private Button btnZoomOut;
private Label lblZoom;
private Label lblCenter;
private Label lblClick;*/
//private ComboBox<MapTypeIdEnum> mapTypeCombo;
private MarkerOptions markerOptions2;
private Marker myMarker2;
private Button btnHideMarker;
private Button btnDeleteMarker;
private Controller controller;
private Set<String> phones;
private LocalDate date;

@Override
public void start(final Stage stage) throws Exception {
mapComponent = new GoogleMapView();
mapComponent.addMapInializedListener(this);
BorderPane bp = new BorderPane();
bp.setPrefSize(600, 400);
// ToolBar tb = new ToolBar();
/*
btnZoomIn = new Button("Zoom In");
btnZoomIn.setOnAction(e -> {
map.zoomProperty().set(map.getZoom() + 1);
});
btnZoomIn.setDisable(true);
btnZoomOut = new Button("Zoom Out");
btnZoomOut.setOnAction(e -> {
map.zoomProperty().set(map.getZoom() - 1);
});
btnZoomOut.setDisable(true);
lblZoom = new Label();
lblCenter = new Label();
lblClick = new Label();
mapTypeCombo = new ComboBox<>();
mapTypeCombo.setOnAction( e -> {
map.setMapType(mapTypeCombo.getSelectionModel().getSelectedItem() );
});
mapTypeCombo.setDisable(true);
Button btnType = new Button("Map type");
btnType.setOnAction(e -> {
map.setMapType(MapTypeIdEnum.HYBRID);
});*//*
btnHideMarker = new Button("Hide Marker");
btnHideMarker.setOnAction(e -> {hideMarker();});
btnDeleteMarker = new Button("Delete Marker");
btnDeleteMarker.setOnAction(e -> {deleteMarker();});*/
// tb.getItems().addAll(btnZoomIn, btnZoomOut, mapTypeCombo,
//new Label("Zoom: "), lblZoom,
//new Label("Center: "), lblCenter,
//new Label("Click: "), lblClick,
// btnHideMarker, btnDeleteMarker);
this.phones = new HashSet<String>();
Button numbers = new Button("Номера");
DatePicker calendar = new DatePicker();
numbers.setMinSize(100, 30);
Button but = new Button("Загрузить");
but.setMinSize(100, 30);
but.setOnAction(e ->{
try {
 
this.controller = new Controller();

this.controller.readFromFile();
} catch (IOException ex) {
ex.printStackTrace();
} catch (InvalidFormatException ex) {
ex.printStackTrace();
}
//this.date = calendar.getValue();
});
//get date value
calendar.getValue();
ListView<String> list = new ListView<String>();
list.setPrefWidth(150);
    numbers.setOnAction(e->{
       LocalDate date4 = calendar.getValue();
   controller.setList(list, calendar.getValue());
});


VBox box = new VBox();
HBox hbox= new HBox();
hbox.setPrefWidth(300);
box.setPrefWidth(150.0);
box.getChildren().add(calendar);
box.getChildren().add(numbers);
box.getChildren().add(but);
hbox.getChildren().add(box);
hbox.getChildren().add(list);
bp.setLeft(hbox);
bp.setCenter(mapComponent);
AnchorPane split = new AnchorPane();

Scene scene = new Scene(bp);
stage.setScene(scene);
stage.show();
}
public void mapInitialized() {
//Once the map has been loaded by the Webview, initialize the map details.
LatLong center = new LatLong(47.606189, -122.335842);
mapComponent.addMapReadyListener(() -> {
// This call will fail unless the map is completely ready.
checkCenter(center);
});
MapOptions options = new MapOptions();
options.center(center)
.mapMarker(true)
.zoom(9)
.overviewMapControl(false)
.panControl(false)
.rotateControl(false)
.scaleControl(false)
.streetViewControl(false)
.zoomControl(false)
.mapType(MapTypeIdEnum.TERRAIN);
map = mapComponent.createMap(options);
//map.setHeading(123.2);

// System.out.println("Heading is: " + map.getHeading() );
MarkerOptions markerOptions = new MarkerOptions();
LatLong markerLatLong = new LatLong(43.239489, 76.894621);
markerOptions.position(markerLatLong)
.title("My new Marker")
.animation(Animation.DROP)
.visible(true);
final Marker myMarker = new Marker(markerOptions);
markerOptions2 = new MarkerOptions();
LatLong markerLatLong2 = new LatLong(43.239852, 76.903639);
markerOptions2.position(markerLatLong2)
.title("My new Marker")
.visible(true);
myMarker2 = new Marker(markerOptions2);
map.addMarker(myMarker);
map.addMarker(myMarker2);
InfoWindowOptions infoOptions = new InfoWindowOptions();
infoOptions.content("<h2>What's up :)</h2><h3>It works finally :)</h3>")
.position(center);
InfoWindow window = new InfoWindow(infoOptions);
window.open(map, myMarker);
//map.fitBounds(new LatLongBounds(new LatLong(30, 120), center));
// System.out.println("Bounds : " + map.getBounds());
//lblCenter.setText(map.getCenter().toString());
map.centerProperty().addListener((ObservableValue<? extends LatLong> obs, LatLong o, LatLong n) -> {
//lblCenter.setText(n.toString());
});
//lblZoom.setText(Integer.toString(map.getZoom()));
map.zoomProperty().addListener((ObservableValue<? extends Number> obs, Number o, Number n) -> {
//lblZoom.setText(n.toString());
});
// map.addStateEventHandler(MapStateEventType.center_changed, () -> {
// System.out.println("center_changed: " + map.getCenter());
// });
// map.addStateEventHandler(MapStateEventType.tilesloaded, () -> {
// System.out.println("We got a tilesloaded event on the map");
// });
map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
//System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
//lblClick.setText(ll.toString());
});
//btnZoomIn.setDisable(false);
//btnZoomOut.setDisable(false);
//mapTypeCombo.setDisable(false);
//mapTypeCombo.getItems().addAll( MapTypeIdEnum.ALL );
LatLong[] ary = new LatLong[]{markerLatLong, markerLatLong2};
MVCArray mvc = new MVCArray(ary);
PolylineOptions polyOpts = new PolylineOptions()
.path(mvc)
.strokeColor("red")
.strokeWeight(2);
Polyline poly = new Polyline(polyOpts);
map.addMapShape(poly);
map.addUIEventHandler(poly, UIEventType.click, (JSObject obj) -> {
LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
// System.out.println("You clicked the line at LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
});
LatLong poly1 = new LatLong(47.429945, -122.84363);
LatLong poly2 = new LatLong(47.361153, -123.03040);
LatLong poly3 = new LatLong(47.387193, -123.11554);
LatLong poly4 = new LatLong(47.585789, -122.96722);
LatLong[] pAry = new LatLong[]{poly1, poly2, poly3, poly4};
MVCArray pmvc = new MVCArray(pAry);
PolygonOptions polygOpts = new PolygonOptions()
.paths(pmvc)
.strokeColor("blue")
.strokeWeight(2)
.editable(false)
.fillColor("lightBlue")
.fillOpacity(0.5);
Polygon pg = new Polygon(polygOpts);
map.addMapShape(pg);
map.addUIEventHandler(pg, UIEventType.click, (JSObject obj) -> {
//polygOpts.editable(true);
pg.setEditable(!pg.getEditable());
});
LatLong centreC = new LatLong(47.545481, -121.87384);
CircleOptions cOpts = new CircleOptions()
.center(centreC)
.radius(5000)
.strokeColor("green")
.strokeWeight(2)
.fillColor("orange")
.fillOpacity(0.3);
Circle c = new Circle(cOpts);
map.addMapShape(c);
map.addUIEventHandler(c, UIEventType.click, (JSObject obj) -> {
c.setEditable(!c.getEditable());
});
LatLongBounds llb = new LatLongBounds(new LatLong(47.533893, -122.89856), new LatLong(47.580694, -122.80312));
RectangleOptions rOpts = new RectangleOptions()
.bounds(llb)
.strokeColor("black")
.strokeWeight(2)
.fillColor("null");
Rectangle rt = new Rectangle(rOpts);
map.addMapShape(rt);
LatLong arcC = new LatLong(47.227029, -121.81641);
double startBearing = 0;
double endBearing = 30;
double radius = 30000;
MVCArray path = ArcBuilder.buildArcPoints(arcC, startBearing, endBearing, radius);
path.push(arcC);
Polygon arc = new Polygon(new PolygonOptions()
.paths(path)
.strokeColor("blue")
.fillColor("lightBlue")
.fillOpacity(0.3)
.strokeWeight(2)
.editable(false));
map.addMapShape(arc);
map.addUIEventHandler(arc, UIEventType.click, (JSObject obj) -> {
arc.setEditable(!arc.getEditable());
});
// LatLong ll = new LatLong(-41.2, 145.9);
// LocationElevationRequest ler = new LocationElevationRequest(new LatLong[]{ll});
//
// ElevationService es = new ElevationService();
// es.getElevationForLocations(ler, new ElevationServiceCallback() {
// @Override
// public void elevationsReceived(ElevationResult[] results, ElevationStatus status) {
//// System.out.println("We got results from the Location Elevation request:");
// for (ElevationResult er : results) {
// System.out.println("LER: " + er.getElevation());
// }
// }
// });
// LatLong lle = new LatLong(-42.2, 145.9);
// PathElevationRequest per = new PathElevationRequest(new LatLong[]{ll, lle}, 3);
//
// ElevationService esb = new ElevationService();
// esb.getElevationAlongPath(per, new ElevationServiceCallback() {
// @Override
// public void elevationsReceived(ElevationResult[] results, ElevationStatus status) {
//// System.out.println("We got results from the Path Elevation Request:");
// for (ElevationResult er : results) {
// System.out.println("PER: " + er.getElevation());
// }
// }
// });
// MaxZoomService mzs = new MaxZoomService();
// mzs.getMaxZoomAtLatLng(lle, new MaxZoomServiceCallback() {
// @Override
// public void maxZoomReceived(MaxZoomResult result) {
// System.out.println("Max Zoom Status: " + result.getStatus());
// System.out.println("Max Zoom: " + result.getMaxZoom());
// }
// });
}

private void hideMarker() {
// System.out.println("deleteMarker");
//boolean visible = myMarker2.getVisible();
//System.out.println("Marker was visible? " + visible);
//myMarker2.setVisible(! visible);
// markerOptions2.visible(Boolean.FALSE);
// myMarker2.setOptions(markerOptions2);
// System.out.println("deleteMarker - made invisible?");
}
private void deleteMarker() {
//System.out.println("Marker was removed?");
map.removeMarker(myMarker2);
}
private void checkCenter(LatLong center) {
// System.out.println("Testing fromLatLngToPoint using: " + center);
// Point2D p = map.fromLatLngToPoint(center);
// System.out.println("Testing fromLatLngToPoint result: " + p);
// System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
}
/**
* The main() method is ignored in correctly deployed JavaFX application.
* main() serves only as fallback in case the application can not be
* launched through deployment artifacts, e.g., in IDEs with limited FX
* support. NetBeans ignores main().
*
* @param args the command line arguments
*/
public static void main(String[] args) {
launch(args);
}}