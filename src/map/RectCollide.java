package map;
/*
 * rect1[0]：矩形1左上角横坐标
 * rect1[1]：矩形1左上角纵坐标
 * rect1[2]：矩形1右下角横坐标
 * rect1[3]：矩形1右下角纵坐标
 * rect2[0]：矩形2左上角横坐标
 * rect2[1]：矩形2左上角纵坐标
 * rect2[2]：矩形2右下角横坐标
 * rect2[3]：矩形2右下角纵坐标
 */
public class RectCollide {
	public static boolean isCollding(int[] rect1, int[] rect2){
		if (rect1[0] > rect2[2]) return false; 
		if (rect1[2] < rect2[0]) return false;
		if (rect1[1] > rect2[3]) return false;
		if (rect1[3] < rect2[1]) return false;
		return true;
	}
}
