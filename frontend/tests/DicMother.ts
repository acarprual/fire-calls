import { Dic } from "../src/domain/Dic";

export class DicMother {
	static create(params?: Partial<Dic>): Dic {
		const defaultParams: Dic = {
			"Multiple Medic Resp 14 Per": 1,
			"Medic Response": 1,
			"Medic Response- Overdose": 1,
			...params,
		};

		return defaultParams;
	}
}
