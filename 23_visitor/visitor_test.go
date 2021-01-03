package visitor

func ExampleRequestVisitor() {
	c := &CustomerCol{}
	c.Add(NewEnterpriseCustomer("A company"))
	c.Add(NewEnterpriseCustomer("B company"))
	c.Add(NewIndividualCustomer("bob"))
	c.Accept(&ServiceRequestVisitor{})
	// Output:
	// serving enterprise customer A company
	// serving enterprise customer B company
	// serving individual customer bob
}

func ExampleAnalysis() {
	c := &CustomerCol{}
	c.Add(NewEnterpriseCustomer("A company"))
	c.Add(NewIndividualCustomer("bob"))
	c.Add(NewEnterpriseCustomer("B company"))
	c.Accept(&AnalysisVisitor{})
	// Output:
	// analysis enterprise customer A company
	// analysis enterprise customer B company
}
