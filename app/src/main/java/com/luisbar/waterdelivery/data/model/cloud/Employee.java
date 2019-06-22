package com.luisbar.waterdelivery.data.model.cloud;


import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;

public class Employee extends ModelRepository<Employee.EmployeeNested> {

    public Employee(){
        super("EmployeeNested", "Tc002s", EmployeeNested.class);
    }

    public static class EmployeeNested extends Model {

        private Integer cbnumi;
        private String cbdesc;
        private String cbdirec;
        private String cbtelef;
        private Integer cbcat;
        private Double cbsal;
        private String cbci;
        private String cbobs;
        private String cbfnac;
        private String cbfing;
        private String cbfret;
        private Object cbfot;
        private Boolean cbest;
        private String cbeciv;
        private String cbfact;
        private String cbhact;
        private String cbuact;

        /**
         * @return The cbnumi
         */
        public Integer getCbnumi() {
            return cbnumi;
        }

        /**
         * @param cbnumi The cbnumi
         */
        public void setCbnumi(Integer cbnumi) {
            this.cbnumi = cbnumi;
        }

        /**
         * @return The cbdesc
         */
        public String getCbdesc() {
            return cbdesc;
        }

        /**
         * @param cbdesc The cbdesc
         */
        public void setCbdesc(String cbdesc) {
            this.cbdesc = cbdesc;
        }

        /**
         * @return The cbdirec
         */
        public String getCbdirec() {
            return cbdirec;
        }

        /**
         * @param cbdirec The cbdirec
         */
        public void setCbdirec(String cbdirec) {
            this.cbdirec = cbdirec;
        }

        /**
         * @return The cbtelef
         */
        public String getCbtelef() {
            return cbtelef;
        }

        /**
         * @param cbtelef The cbtelef
         */
        public void setCbtelef(String cbtelef) {
            this.cbtelef = cbtelef;
        }

        /**
         * @return The cbcat
         */
        public Integer getCbcat() {
            return cbcat;
        }

        /**
         * @param cbcat The cbcat
         */
        public void setCbcat(Integer cbcat) {
            this.cbcat = cbcat;
        }

        /**
         * @return The cbsal
         */
        public Double getCbsal() {
            return cbsal;
        }

        /**
         * @param cbsal The cbsal
         */
        public void setCbsal(Double cbsal) {
            this.cbsal = cbsal;
        }

        /**
         * @return The cbci
         */
        public String getCbci() {
            return cbci;
        }

        /**
         * @param cbci The cbci
         */
        public void setCbci(String cbci) {
            this.cbci = cbci;
        }

        /**
         * @return The cbobs
         */
        public String getCbobs() {
            return cbobs;
        }

        /**
         * @param cbobs The cbobs
         */
        public void setCbobs(String cbobs) {
            this.cbobs = cbobs;
        }

        /**
         * @return The cbfnac
         */
        public String getCbfnac() {
            return cbfnac;
        }

        /**
         * @param cbfnac The cbfnac
         */
        public void setCbfnac(String cbfnac) {
            this.cbfnac = cbfnac;
        }

        /**
         * @return The cbfing
         */
        public String getCbfing() {
            return cbfing;
        }

        /**
         * @param cbfing The cbfing
         */
        public void setCbfing(String cbfing) {
            this.cbfing = cbfing;
        }

        /**
         * @return The cbfret
         */
        public String getCbfret() {
            return cbfret;
        }

        /**
         * @param cbfret The cbfret
         */
        public void setCbfret(String cbfret) {
            this.cbfret = cbfret;
        }

        /**
         * @return The cbfot
         */
        public Object getCbfot() {
            return cbfot;
        }

        /**
         * @param cbfot The cbfot
         */
        public void setCbfot(Object cbfot) {
            this.cbfot = cbfot;
        }

        /**
         * @return The cbest
         */
        public Boolean getCbest() {
            return cbest;
        }

        /**
         * @param cbest The cbest
         */
        public void setCbest(Boolean cbest) {
            this.cbest = cbest;
        }

        /**
         * @return The cbeciv
         */
        public String getCbeciv() {
            return cbeciv;
        }

        /**
         * @param cbeciv The cbeciv
         */
        public void setCbeciv(String cbeciv) {
            this.cbeciv = cbeciv;
        }

        /**
         * @return The cbfact
         */
        public String getCbfact() {
            return cbfact;
        }

        /**
         * @param cbfact The cbfact
         */
        public void setCbfact(String cbfact) {
            this.cbfact = cbfact;
        }

        /**
         * @return The cbhact
         */
        public String getCbhact() {
            return cbhact;
        }

        /**
         * @param cbhact The cbhact
         */
        public void setCbhact(String cbhact) {
            this.cbhact = cbhact;
        }

        /**
         * @return The cbuact
         */
        public String getCbuact() {
            return cbuact;
        }

        /**
         * @param cbuact The cbuact
         */
        public void setCbuact(String cbuact) {
            this.cbuact = cbuact;
        }
    }
}
