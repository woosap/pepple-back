package woosap.Pepple.entity.type;

public enum Job {
    PLANNER("기획자", 1),
    DESIGNER("GUI 디자이너", 2),
    MARKETER("마케터", 3),
    FRONTEND("프론트드자 개발자", 4),
    BACKEND("백앤드 개발자", 5);

    private final String job;
    private final int number;

    Job(String job, int number) {
        this.job = job;
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public String getJob() {
        return this.job;
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
