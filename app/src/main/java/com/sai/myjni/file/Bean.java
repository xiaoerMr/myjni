package com.sai.myjni.file;

import java.util.List;

public class Bean {
    /**
     * type : FeatureCollection
     * name : 训练空域
     * crs : {"type":"name","properties":{"name":"urn:ogc:def:crs:OGC:1.3:CRS84"}}
     * features : [{"type":"Feature","properties":{"标识":"ZSGM10","名称":"高密十号"},"geometry":{"type":"MultiPolygon","coordinates":[[[[119.87918835003116,36.668888178019074],[119.868073155172,36.44806357408284],[119.66807179043673,36.4480659294075],[119.6680757188059,36.664724170939785],[119.87918835003116,36.668888178019074]]]]}},{"type":"Feature","properties":{"标识":"ZSGM15","名称":"高密十五号"},"geometry":{"type":"MultiPolygon","coordinates":[[[[118.15139481549203,36.48141575454139],[118.15137280873802,35.21480079627971],[117.90137094621717,35.21480362906162],[117.60139061726272,36.48142217369277],[118.15139481549203,36.48141575454139]]]]}},{"type":"Feature","properties":{"标识":"ZSLQ367","名称":"路桥367"},"geometry":{"type":"MultiPolygon","coordinates":[[[[121.61796078489853,28.165149299440287],[121.63463281489298,28.598454265520918],[122.26796956376623,28.59844818928805],[121.8846289049053,28.165146778524722],[121.61796078489853,28.165149299440287]]]]}}]
     */

    private String type;
    private String name;
    private CrsBean crs;
    private List<FeaturesBean> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrsBean getCrs() {
        return crs;
    }

    public void setCrs(CrsBean crs) {
        this.crs = crs;
    }

    public List<FeaturesBean> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesBean> features) {
        this.features = features;
    }

    public static class CrsBean {
        /**
         * type : name
         * properties : {"name":"urn:ogc:def:crs:OGC:1.3:CRS84"}
         */

        private String type;
        private PropertiesBean properties;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public PropertiesBean getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBean properties) {
            this.properties = properties;
        }

        public static class PropertiesBean {
            /**
             * name : urn:ogc:def:crs:OGC:1.3:CRS84
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class FeaturesBean {
        /**
         * type : Feature
         * properties : {"标识":"ZSGM10","名称":"高密十号"}
         * geometry : {"type":"MultiPolygon","coordinates":[[[[119.87918835003116,36.668888178019074],[119.868073155172,36.44806357408284],[119.66807179043673,36.4480659294075],[119.6680757188059,36.664724170939785],[119.87918835003116,36.668888178019074]]]]}
         */

        private String type;
        private PropertiesBeanX properties;
        private GeometryBean geometry;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public PropertiesBeanX getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBeanX properties) {
            this.properties = properties;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public static class PropertiesBeanX {
            /**
             * 标识 : ZSGM10
             * 名称 : 高密十号
             */

            private String 标识;
            private String 名称;

            @Override
            public String toString() {
                return "PropertiesBeanX{" +
                        "标识='" + 标识 + '\'' +
                        ", 名称='" + 名称 + '\'' +
                        '}';
            }

            public String get标识() {
                return 标识;
            }

            public void set标识(String 标识) {
                this.标识 = 标识;
            }

            public String get名称() {
                return 名称;
            }

            public void set名称(String 名称) {
                this.名称 = 名称;
            }
        }

        public static class GeometryBean {
            /**
             * type : MultiPolygon
             * coordinates : [[[[119.87918835003116,36.668888178019074],[119.868073155172,36.44806357408284],[119.66807179043673,36.4480659294075],[119.6680757188059,36.664724170939785],[119.87918835003116,36.668888178019074]]]]
             */

            private String type;
            private List<List<List<List<Double>>>> coordinates;

            @Override
            public String toString() {
                return "GeometryBean{" +
                        "type='" + type + '\'' +
                        ", coordinates=" + coordinates +
                        '}';
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<List<List<List<Double>>>> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<List<List<List<Double>>>> coordinates) {
                this.coordinates = coordinates;
            }
        }
    }
}
