describe("FireCallsForm", () => {
	it("successfully loads", () => {
		cy.visit("/");
		cy.findByRole("heading", { name: /Current fire calls/i }).should("exist");
	});
});
