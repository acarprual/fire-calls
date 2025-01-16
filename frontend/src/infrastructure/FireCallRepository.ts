import { Dic } from "../domain/Dic";

export class FireCallRepository {
	private readonly fireCallsEndpoint = "/api/fire_calls";

	async search(x: number, y: number, radius: number, type: string): Promise<Dic | undefined> {
		const url = this.fireCallsEndpoint;
		const requestOptions = {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ x, y, r: radius, type }),
		};

		const response = fetch(url, requestOptions)
			.then<Dic>((response) => {
				return response.json();
			})
			.catch((err) => {
				return undefined;
			});

		return response;
	}
}
