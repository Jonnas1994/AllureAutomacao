package DTO;

import java.util.List;

public class TestCase {

    public List<Case> cases;

    public class SubComand
    {
        public String description;
        public String comand;
        public String findBy;
        public String target;
        public String value;
    }

    public class Command
    {
        public String comand;
        public String findBy;
        public String target;
        public String value;
        public List<SubComand> subComand;
    }

    public class Step
    {
        public String description;
        public List<Command> commands;
    }

    public class Case
    {
        public String nameClass;
        public String name;
        public String description;
        public List<Step> steps;
    }

}