package woosap.guide.type;

public enum Category {
    PLAN("기획", 1),
    DESIGN("디자인", 2),
    STUDY("스터디", 3),
    DEVELOP("개발", 4);

    private String category;
    private int number;

    Category(String category, int number) {
        this.category = category;
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public String getCategory() {
        return this.category;
    }

    public String matchByNumber(int number) {
        Job[] jobs = Job.values();
        for (Job job : jobs) {
            if (job.getNumber() == number) {
                return job.getJob();
            }
        }
        return null;
    }
}
