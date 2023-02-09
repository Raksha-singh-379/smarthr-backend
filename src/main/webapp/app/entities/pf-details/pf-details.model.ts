import dayjs from 'dayjs/esm';

export interface IPfDetails {
  id: number;
  isPfContribution?: boolean | null;
  pfNumber?: string | null;
  pfRate?: number | null;
  additionalPfRate?: string | null;
  totalPfRate?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  employeId?: number | null;
  reEnumerationId?: number | null;
}

export type NewPfDetails = Omit<IPfDetails, 'id'> & { id: null };
