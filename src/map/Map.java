package map;
/*
 * 地图元素说明：
 * 0：空
 * 1：墙、木墙
 * 2：铁墙、钢
 * 3：水、河、海
 * 4：草、树
 * 5：老巢、鹰、家
 * 6：p1
 * 7：p2
 * 8：敌方坦克
 */
public class Map{
	public static int[][] map1 = {
			{8,0,0,0,0,0,8,0,0,0,0,0,8,},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,},
			{0,1,0,1,0,1,2,1,0,1,0,1,0,},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,},
			{0,1,0,1,0,0,0,0,0,1,0,1,0,},
			{0,0,0,0,0,1,0,1,0,0,0,0,0,},
			{2,0,1,1,0,0,0,0,0,1,1,0,2,},
			{0,0,0,0,0,1,1,1,0,0,0,0,0,},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,},
			{0,1,0,1,0,0,0,0,0,1,0,1,0,},
			{0,1,0,1,0,1,1,1,0,1,0,1,0,},
			{0,0,0,0,6,1,5,1,7,0,0,0,0,}
	};
	public static int[][] map2 = {
			{8,0,0,2,0,0,8,2,0,0,0,0,8,},
			{0,1,0,2,0,0,0,1,0,1,0,1,0,},
			{0,1,0,0,0,0,1,1,0,1,2,1,0,},
			{0,0,0,1,0,0,0,0,0,2,0,0,0,},
			{4,0,0,1,0,0,2,0,0,1,4,1,2,},
			{4,4,0,0,0,1,0,0,2,0,4,0,0,},
			{0,1,1,1,4,4,4,2,0,0,4,1,0,},
			{0,0,0,2,4,1,0,1,0,1,0,1,0,},
			{2,1,0,2,0,1,0,1,0,0,0,1,0,},
			{0,1,0,1,0,1,1,1,0,1,2,1,0,},
			{0,1,0,1,0,1,1,1,0,0,0,0,0,},
			{0,1,0,0,0,1,1,1,0,1,0,1,0,},
			{0,1,0,1,6,1,5,1,7,1,1,1,0,}
	};
	public static int[][] map3 = {
			{8,0,0,0,1,0,8,0,1,0,0,0,8,},
			{0,4,4,4,1,0,0,0,0,0,2,2,2,},
			{1,4,4,4,0,0,0,0,0,0,0,0,0,},
			{4,4,4,4,0,0,0,1,0,1,1,1,1,},
			{4,4,4,4,1,1,1,1,0,1,0,1,0,},
			{4,4,4,4,0,0,1,0,0,0,0,1,0,},
			{0,4,0,0,0,0,2,2,2,0,0,4,0,},
			{0,1,0,1,0,0,0,0,0,4,4,4,4,},
			{1,1,1,2,1,1,1,1,1,4,4,4,4,},
			{0,0,0,0,0,1,0,1,1,4,4,4,4,},
			{1,0,0,2,0,0,0,1,1,4,4,4,0,},
			{1,1,0,2,0,1,1,1,0,4,4,4,0,},
			{2,1,1,0,6,1,5,1,7,1,0,0,0,}
	};
	public static int[][] map4 = {
			{8,0,0,0,1,1,8,0,0,0,0,0,8,},
			{2,0,1,0,1,0,0,0,2,2,2,0,0,},
			{2,0,1,0,0,0,1,0,0,0,0,0,0,},
			{1,0,1,1,1,0,1,1,0,3,3,0,3,},
			{1,0,0,0,1,0,0,0,0,3,0,0,0,},
			{0,0,1,0,3,3,0,3,3,3,0,1,1,},
			{1,1,0,0,3,1,0,1,1,0,0,0,0,},
			{0,0,0,0,3,0,0,0,0,0,2,2,0,},
			{3,3,3,0,3,0,2,0,1,0,2,0,0,},
			{0,0,0,1,1,0,0,0,0,0,2,1,1,},
			{0,0,0,0,1,1,1,1,1,1,0,0,0,},
			{1,1,1,0,0,1,1,1,0,1,1,0,0,},
			{1,0,0,0,6,1,5,1,7,0,0,0,0,}
	};
	public static int[][] map5 = {
			{8,0,1,0,0,1,8,1,0,1,0,0,8,},
			{4,1,1,1,0,1,0,2,0,1,1,0,0,},
			{4,4,4,0,0,1,0,1,0,1,0,1,1,},
			{4,3,3,3,3,3,3,3,3,3,3,0,3,},
			{0,1,0,0,0,0,1,1,0,0,0,0,0,},
			{0,0,1,0,0,1,1,1,1,1,1,2,2,},
			{1,1,0,1,0,1,1,1,4,1,2,2,1,},
			{0,0,0,2,0,2,0,4,4,4,4,0,0,},
			{3,3,0,3,3,3,3,3,0,4,4,4,4,},
			{4,4,0,1,0,0,1,1,0,0,0,0,0,},
			{4,4,1,1,0,1,1,1,0,2,1,1,0,},
			{4,2,1,0,1,1,1,1,0,1,0,1,0,},
			{0,0,0,0,6,1,5,1,7,1,0,1,0,}
	};
	public static int[][] map6 = {
			{8,0,0,0,0,0,8,0,2,2,2,4,8,},
			{0,1,0,0,0,0,0,0,2,0,0,2,0,},
			{1,4,1,0,0,0,1,1,1,1,0,2,0,},
			{0,1,4,1,0,0,1,0,4,1,2,2,0,},
			{0,0,1,0,4,2,1,4,0,1,0,0,0,},
			{0,0,0,0,2,0,1,2,1,1,0,0,0,},
			{0,0,1,1,2,1,0,2,0,0,0,0,0,},
			{0,0,1,0,4,1,2,4,0,0,0,0,0,},
			{2,2,2,4,0,1,0,0,1,1,0,0,0,},
			{2,0,1,1,1,1,0,0,1,2,2,0,0,},
			{2,0,0,2,0,0,0,0,0,2,1,1,0,},
			{4,2,2,2,0,1,1,1,0,0,1,2,2,},
			{0,0,0,0,6,1,5,1,7,0,0,2,2,}
	};
	public static int[][] map7 = {
			{8,1,0,1,0,1,8,1,0,1,0,1,8,},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,},
			{0,2,0,2,0,2,0,2,0,2,0,2,0,},
			{1,0,1,0,1,0,0,0,1,0,1,0,1,},
			{1,0,1,1,1,0,1,0,1,1,1,0,1,},
			{2,0,2,0,2,0,2,0,2,0,2,0,2,},
			{4,4,0,0,1,0,4,0,1,0,0,4,4,},
			{4,4,4,4,1,1,4,1,1,4,4,4,4,},
			{4,4,4,4,4,4,4,4,4,4,4,4,4,},
			{1,0,1,0,1,4,4,4,1,0,1,0,1,},
			{0,1,0,1,0,0,4,0,0,1,0,1,0,},
			{0,1,0,1,0,1,1,1,0,1,0,1,0,},
			{0,1,0,1,6,1,5,1,7,1,0,1,0,}
	};
}
