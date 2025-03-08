enum Category {
    SOFTWARE, HARDWARE;
}

class Employee {
    String fullName;
    int pointLevel;
    Category assignedCategory;
    public Employee(String fullName, int pointLevel, Category assignedCategory) {
        this.fullName = fullName;
        this.pointLevel = pointLevel;
        this.assignedCategory = assignedCategory;
    }
}

class Ticket {
    int id;
    String name;
    Category category;
    int point;
    String assignedEmployee="";
    boolean isCompleted=false;

    public Ticket(int id, String name, Category category, int point) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.point = point;
    }
}

class HelpDesk {
    Employee emp1, emp2;
    Ticket[] tickets = new Ticket[5];

    void addEmployee(Employee e, int pos) {
        if (pos == 1) {
            emp1 = e;
        }
       else{
        emp2 = e;
       }

    }

    void addTicket(Ticket t, int pos) {
        if (pos >= 1 && pos <= 5) {
            tickets[pos - 1] = t;
        }
    }

    void completeTicket(String employeeName, int ticketId) {
        Employee emp = null;

      if (emp1 != null && emp1.fullName.equals(employeeName)) {
           emp = emp1;
       } else if (emp2 != null && emp2.fullName.equals(employeeName)) {
          emp = emp2;
      }


        if (emp == null) return;

        for (Ticket t : tickets) {
            if (t != null && t.id == ticketId && !t.isCompleted) {
                if (emp.pointLevel >= t.point) {
                    t.isCompleted = true;
                    t.assignedEmployee = emp.fullName;
                    System.out.println("Ticket " + t.id + " marked as completed by " + emp.fullName + ".");
                } else {
                    System.out.println("Ticket " + t.id + " cannot be completed by " + emp.fullName + " (Insufficient points).");
                }
                return;
            }
        }
    }

    int getWaitingTicketCount() {
        int count = 0;
        for (Ticket t : tickets) {
            if (t != null && !t.isCompleted)
            {
                 count++;
            }
        }
        return count;
    }

    int getCompletedTicketsTotalPoint() {
        int sum = 0;
        for (Ticket t : tickets) {
            if (t != null && t.isCompleted) sum += t.point;
        }
        return sum;
    }
}

public class HelpDeskMan{
    public static void main(String[] args) {
        HelpDesk helpDesk = new HelpDesk();
        Employee alice = new Employee("Alice Brown", 5, Category.SOFTWARE);
        Employee bob = new Employee("Bob Smith", 8, Category.HARDWARE);
        helpDesk.addEmployee(alice, 1);
        helpDesk.addEmployee(bob, 2);

        Ticket t1 = new Ticket(101, "Software Bug", Category.SOFTWARE, 4);
        Ticket t2 = new Ticket(102, "Network Issue", Category.HARDWARE, 7);
        Ticket t3 = new Ticket(103, "System Crash", Category.HARDWARE, 10);
        Ticket t4 = new Ticket(104, "Printer Not Working", Category.HARDWARE, 3);
        Ticket t5 = new Ticket(105, "UI Bug", Category.SOFTWARE, 2);
        helpDesk.addTicket(t1, 1);
        helpDesk.addTicket(t2, 2);
        helpDesk.addTicket(t3, 3);
        helpDesk.addTicket(t4, 4);
        helpDesk.addTicket(t5, 5);

        helpDesk.completeTicket("Alice Brown", 101);
        helpDesk.completeTicket("Bob Smith", 102);
        helpDesk.completeTicket("Alice Brown", 103);
        helpDesk.completeTicket("Bob Smith", 104);
        helpDesk.completeTicket("Alice Brown", 105);

        System.out.println(helpDesk.getWaitingTicketCount());
        System.out.println(helpDesk.getCompletedTicketsTotalPoint());
    }
}