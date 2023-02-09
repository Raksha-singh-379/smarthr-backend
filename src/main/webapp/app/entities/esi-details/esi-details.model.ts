import dayjs from 'dayjs/esm';

export interface IEsiDetails {
  id: number;
  isEsiContribution?: boolean | null;
  esiNumber?: string | null;
  esiRate?: number | null;
  additionalEsiRate?: string | null;
  totalEsiRate?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  employeId?: number | null;
  reEnumerationId?: number | null;
}

export type NewEsiDetails = Omit<IEsiDetails, 'id'> & { id: null };
