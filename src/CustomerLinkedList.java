public class CustomerLinkedList {
    CustomerNode head;

    public CustomerLinkedList() {
        this.head = null;
    }

    public Customer addCustomer(String firstname, String lastname) {

        String customerId = CustomerIDGenerator.generateCustomerID();
        Customer customer = new Customer(customerId,firstname,lastname);
        CustomerNode newNode = new CustomerNode(customer);

        if (head == null) {
            head = newNode;

        } else {
            CustomerNode temp = head;
            while (temp.next!= null) {
                temp = temp.next;

            }
            temp.next = newNode;

        }
        System.out.println("Yeni müşteri ekledi: " + customer.getCustomerId() + " - " + customer.getFirstname() + " " + customer.getLastname());

        return customer;
    }



}
