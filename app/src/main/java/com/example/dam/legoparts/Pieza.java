package com.example.dam.legoparts;

/**
 * Created by DAM on 2/2/17.
 */


import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
    import java.util.List;
    import com.google.gson.annotations.Expose;
    import com.google.gson.annotations.SerializedName;

    public class Pieza {

        @SerializedName("part_num")
        @Expose
        private String partNum;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("part_cat_id")
        @Expose
        private Integer partCatId;
        @SerializedName("year_from")
        @Expose
        private Integer yearFrom;
        @SerializedName("year_to")
        @Expose
        private Integer yearTo;
        @SerializedName("part_url")
        @Expose
        private String partUrl;
        @SerializedName("part_img_url")
        @Expose
        private String partImgUrl;
        @SerializedName("prints")
        @Expose
        private List<Object> prints = null;
        @SerializedName("molds")
        @Expose
        private List<Object> molds = null;
        @SerializedName("alternates")
        @Expose
        private List<Object> alternates = null;
        @SerializedName("external_ids")
        @Expose
        private ExternalIds externalIds;

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPartCatId() {
            return partCatId;
        }

        public void setPartCatId(Integer partCatId) {
            this.partCatId = partCatId;
        }

        public Integer getYearFrom() {
            return yearFrom;
        }

        public void setYearFrom(Integer yearFrom) {
            this.yearFrom = yearFrom;
        }

        public Integer getYearTo() {
            return yearTo;
        }

        public void setYearTo(Integer yearTo) {
            this.yearTo = yearTo;
        }

        public String getPartUrl() {
            return partUrl;
        }

        public void setPartUrl(String partUrl) {
            this.partUrl = partUrl;
        }

        public String getPartImgUrl() {
            return partImgUrl;
        }

        public void setPartImgUrl(String partImgUrl) {
            this.partImgUrl = partImgUrl;
        }

        public List<Object> getPrints() {
            return prints;
        }

        public void setPrints(List<Object> prints) {
            this.prints = prints;
        }

        public List<Object> getMolds() {
            return molds;
        }

        public void setMolds(List<Object> molds) {
            this.molds = molds;
        }

        public List<Object> getAlternates() {
            return alternates;
        }

        public void setAlternates(List<Object> alternates) {
            this.alternates = alternates;
        }

        public ExternalIds getExternalIds() {
            return externalIds;
        }

        public void setExternalIds(ExternalIds externalIds) {
            this.externalIds = externalIds;
        }
    }





