import { useState } from "react";

import { FormEvent } from "../../domain/FormEvent";
import { FireCallRepository } from "../../infrastructure/FireCallRepository";
import { useFireCallRepository } from "./useFireCallRepository";

type FormFields = { x: number; y: number; radius: number; type: string };

export function FireCallsForm({ fireCallRepository }: { fireCallRepository: FireCallRepository }) {
	const [x, setX] = useState(47.59815);
	const [y, setY] = useState(-122.33454);
	const [radius, setRadius] = useState(500.0);
	const [type, setType] = useState("med");
	const { fireCalls, isLoading } = useFireCallRepository(fireCallRepository, x, y, radius, type);

	const submitForm = (ev: FormEvent<FormFields>) => {
		ev.preventDefault();
		const { x, y, radius, type } = ev.target.elements;
		setX(x.value);
		setY(y.value);
		setRadius(radius.value);
		setType(type.value);
	};

	return (
		<form className="fire-calls-form" onSubmit={submitForm}>
			<div className="form-group">
				<label className="form-label" htmlFor="type">
					Type
				</label>
				<input className="form-input" type="text" name="type" id="type" defaultValue={type} />
			</div>
			<div className="form-group">
				<label className="form-label" htmlFor="x">
					X coordinate
				</label>
				<input className="form-input" type="number" step="any" name="x" id="x" defaultValue={x} />
			</div>
			<div className="form-group">
				<label className="form-label" htmlFor="y">
					Y coordinate
				</label>
				<input className="form-input" type="number" step="any" name="y" id="y" defaultValue={y} />
			</div>
			<div className="form-group">
				<label className="form-label" htmlFor="radius">
					Radius
				</label>
				<input
					className="form-input"
					type="number"
					step="any"
					name="radius"
					id="radius"
					defaultValue={radius}
				/>
			</div>
			<button type="submit" className={`${isLoading ? "loading" : ""} submit-button`}>
				Search fire calls
			</button>
			<p className="result">{JSON.stringify(fireCalls)}</p>
		</form>
	);
}
