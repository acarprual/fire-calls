import { render, screen } from "@testing-library/react";
import { mock } from "jest-mock-extended";

import { FireCallRepository } from "../../../src/infrastructure/FireCallRepository";
import { FireCallsForm } from "../../../src/sections/fire_calls/FireCallsForm";
import { DicMother } from "../../DicMother";

const mockFireCallRepository = mock<FireCallRepository>();

describe("FireCallsForm", () => {
	it("renders", async () => {
		const dic = DicMother.create();

		mockFireCallRepository.search.mockResolvedValue(dic);

		render(<FireCallsForm fireCallRepository={mockFireCallRepository} />);

		const result = await screen.findByText(
			'{"Multiple Medic Resp 14 Per":1,"Medic Response":1,"Medic Response- Overdose":1}'
		);

		expect(result).toBeInTheDocument();
	});
});
