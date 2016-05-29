package idv.david.flexiblefragment;

public class MyTeam {
    public static final TeamVO[] TEAMS = {
            new TeamVO("紐約洋基隊", R.drawable.p7, "紐約洋基（英語：New York Yankees），是美國職棒大聯盟中，隸屬於美國聯盟的棒球隊伍之一。主場位於紐約的布朗斯區。在美國聯盟的分區中，屬於美聯東區，通常可簡稱為NYY，是美國聯盟八個創史隊之一。在1901年，球隊初創立時的位置在馬里蘭州的巴爾的摩，當時球隊的名稱為巴爾的摩金鶯，但是在1903年時，球隊遷移到紐約，球隊名稱也改為紐約高地人(New York Highlanders)。在1913年則將高地人名稱改為洋基(Yankees)。1923年－2008年，洋基的主場為老洋基體育場，2009年，啟用新的球場新洋基體育場，同年洋基就拿下世界大賽冠軍。"),
            new TeamVO("波士頓紅襪隊", R.drawable.p4, "波士頓紅襪（英語：Boston Red Sox，簡稱：BOS or BRS），是一支職業棒球隊，隸屬於美國職棒大聯盟的美國聯盟東區。其主場自1912年後即位於麻薩諸塞州波士頓的芬威球場。「紅襪」這個名字是在1908年左右由球隊當時的老闆泰勒所取，隊徽也是應此名字而設計。\n" +
                    "\n" +
                    "該球隊創建於1901年紐約州的布法羅市，是美國聯盟最早的八支球隊之一。自從布法羅搬遷到波士頓後，他們即變成了一支舉足輕重的球隊——首先在1903年的世界大賽中擊敗了匹茲堡海盜隊，而後到1918年之間，他們又共奪得了4次世界冠軍，但是在這之後卻進入了美國職業棒球史上最長的一段冠軍乾旱期。很多人認為這是因為「貝比魯斯魔咒」(已於2004年破解)，這個魔咒來源於1920年紅襪將貝比魯斯賣給了紐約洋基隊。這段時間是紅襪隊史上最困難的一段時期，包括了1946年的「瘋狂衝刺」，1968年的「不可能的夢想」，以及1974年和1986年兩次和世界大賽冠軍擦肩而過。這個詛咒終於在2004年結束，紅襪奪得了隊史上的第六個世界大賽冠軍。自從2003年後，紅襪6次進入季後賽，並且奪得了三次世界冠軍，被認為是近幾年來最成功的球隊之一。"),
            new TeamVO("巴爾的摩金鶯隊", R.drawable.p1, "巴爾的摩金鶯（Baltimore Orioles），是美國職棒大聯盟的職業球隊之一，其位置位於美國馬里蘭州巴爾的摩，球隊隸屬於的美國聯盟東區。自1992年後，球隊的主場在金鶯公園一直使用至今。球隊的名字來源於馬里蘭州的州鳥。媒體常將球隊簡稱為O's或Birds。\n" +
                    "\n" +
                    "金鶯是於1901年的時候創立，是美國聯盟八個創立人之一，當時的球隊的位置在威斯康辛州的密爾瓦基，其名稱為密爾瓦基釀酒人。在1902年時，球隊遷移到聖路易，球隊名稱改成聖路易棕人（Saint Louis Browns）。在聖路易待了52年以後，球隊遷移到巴爾的摩直到現在，球隊名稱改為巴爾的摩金鶯。"),
            new TeamVO("多倫多藍鳥隊", R.drawable.p13, "多倫多藍鳥隊（英語：Toronto Blue Jays）（簡稱：TOR or TBJ）是位於加拿大多倫多的美國職棒大聯盟隊伍。他們隸屬於美國聯盟（以下簡稱美聯）的東區，並是唯一一隊曾經贏得世界大賽的非美國隊伍。由於蒙特婁博覽隊在2005年球季起搬移至美國華盛頓而成爲華盛頓國民隊，現時多倫多藍鳥是美國職棒大聯盟中餘下的唯一一隊加拿大隊伍。目前的主場是位於加拿大多倫多的羅傑斯中心，鄰近多倫多地標西恩塔。"),
            new TeamVO("坦帕灣光芒隊", R.drawable.p10, "坦帕灣光芒（Tampa Bay Rays）是位於佛羅里達州聖彼德斯堡的美國職棒大聯盟球隊，隸屬美國聯盟東區。於1998年的大聯盟擴充計畫中加入美國聯盟，主場是純品康納室內球場（Tropicana Field）。")
    };

    static class TeamVO {
        private String name;
        //Drawable ID
        private int logo;
        private String info;

        public TeamVO() {

        }

        public TeamVO(String name, int logo, String info) {
            this.name = name;
            this.logo = logo;
            this.info = info;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
